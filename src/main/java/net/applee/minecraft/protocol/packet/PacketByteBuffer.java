package net.applee.minecraft.protocol.packet;

import net.applee.minecraft.item.Item;
import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.protocol.exceptions.BufferLengthException;
import net.applee.minecraft.protocol.exceptions.DecoderException;
import net.applee.minecraft.protocol.exceptions.EncoderException;
import net.applee.minecraft.protocol.exceptions.OverflowException;
import net.applee.minecraft.protocol.packet.listening.Flater;
import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.hit.BlockHitResult;
import net.applee.minecraft.utils.math.*;
import net.applee.minecraft.utils.string.Text;
import org.cloudburstmc.nbt.NBTInputStream;
import org.cloudburstmc.nbt.NBTOutputStream;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import static net.applee.minecraft.utils.Utils.convertBuffer;
import static net.applee.minecraft.utils.Utils.convertBufferUnsigned;

public class PacketByteBuffer {

    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private final List<Byte> buffer = new ArrayList<>();
    private int readerIndex = 0;

    public PacketByteBuffer() {
    }

    public static int getVarIntLength(int value) {
        for(int i = 1; i < 5; ++i) {
            if ((value & -1 << i * 7) == 0) {
                return i;
            }
        }

        return 5;
    }

    public static int getVarLongLength(long value) {
        for(int i = 1; i < 10; ++i) {
            if ((value & -1L << i * 7) == 0L) {
                return i;
            }
        }

        return 10;
    }


    public PacketByteBuffer(byte[] bytes) {
        writeBytes(bytes);
    }

    public PacketByteBuffer(byte[] bytes, int offset) {
        writeBytes(bytes, offset);
    }

    public PacketByteBuffer(List<Byte> bytes) {
        writeBytes(bytes);
    }

    public PacketByteBuffer(PacketByteBuffer parent) {
        writeBytes(parent);
    }

    private static int toEncodedStringLength(int decodedLength) {
        return decodedLength * 3;
    }

    public static <T> IntFunction<T> getMaxValidator(IntFunction<T> applier, int max) {
        return (value) -> {
            if (value > max) throw new DecoderException("Value " + value + " is larger than limit " + max);
            else return applier.apply(value);
        };
    }

    public boolean canRead() {
        return readerIndex < buffer.size();
    }

    public void writeVarInt(int value) {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                writeByte(value);
                return;
            }

            writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public void writeVarLong(long value) {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                writeByte(value);
                return;
            }

            writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public void writeInt(int value) {
        writeBytes(ByteBuffer.allocate(4).putInt(value).array());
    }

    public void writeLong(long value) {
        writeBytes(ByteBuffer.allocate(8).putLong(value).array());
    }

    public void writeFloat(float value) {
        writeInt(Float.floatToRawIntBits(value));
    }

    public void writeDouble(double value) {
        writeLong(Double.doubleToRawLongBits(value));
    }

    public void writeShort(int value) {
        if (value > 0xFFFF)
            throw new OverflowException(value, 0xFFFF, "short");
        writeShort((short) value);
    }

    public void writeShort(short value) {
        writeBytes(ByteBuffer.allocate(2).putShort(value).array());
    }

    public void writeBoolean(boolean value) {
        writeByte(value ? 1 : 0);
    }

    public void writeString(String string) {
        this.writeString(string, 32767);
    }

    public void writeString(String string, int maxLength) {
        if (string.length() > maxLength)
            throw new EncoderException("String too big (was " + string.length() + " characters, max " + maxLength + ")");
        else {
            byte[] bs = string.getBytes(StandardCharsets.UTF_8);
            int i = toEncodedStringLength(maxLength);
            if (bs.length > i)
                throw new EncoderException("String too big (was " + bs.length + " bytes encoded, max " + i + ")");
            else {
                this.writeVarInt(bs.length);
                this.writeBytes(bs);
            }
        }
    }

    public void writeUuid(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public void writeByte(long value) {
        if (value > 0xFF)
            throw new OverflowException(value, 0xFF, "byte");
        buffer.add((byte) value);
    }

    public void writeByte(int value) {
        if (value > 0xFF)
            throw new OverflowException(value, 0xFF, "byte");
        buffer.add((byte) value);
    }

    public void addBufferLength() {
        addBufferLength(0);
    }

    public void addBufferLength(int extra) {
        byte[] buf = getAllBuffer();
        clearBuffer();

        this.writeVarInt(buf.length + extra);
        this.writeBytes(buf);
    }

    public void writeBytes(PacketByteBuffer buf) {
        for (byte b : buf.getAllBuffer()) buffer.add(b);
    }

    public void writeBytes(byte[] bytes, int offset) {
        for (int i = offset; i < bytes.length; i++) buffer.add(bytes[i]);
    }

    public void writeBytes(byte[] bytes) {
        for (byte b : bytes) buffer.add(b);
    }

    public void writeByteArray(byte[] array) {
        this.writeVarInt(array.length);
        this.writeBytes(array);
    }

    public byte[] readByteArray() {
        int size = this.readVarInt();

        byte[] bs = new byte[size];
        for (int o = 0; o < size; o++) bs[o] = readByte();
        return bs;
    }

    public byte[] readByteArray(int maxSize) {
        int i = this.readVarInt();
        if (i > maxSize)
            throw new DecoderException("ByteArray with size " + i + " is bigger than allowed " + maxSize);
        else {
            byte[] bs = new byte[i];
            for (int o = 0; o < i; o++) bs[o] = readByte();
            return bs;
        }
    }

    public void writeBytes(List<Byte> bytes) {
        buffer.addAll(bytes);
    }

    public int readVarInt() {
        return readVarInt(true);
    }

    public int readVarInt(boolean shiftReader) {
        int value = 0;
        int position = 0;
        byte currentByte;

        int reader = readerIndex;
        while (true) {
            currentByte = readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;
            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }
        if (!shiftReader) readerIndex = reader;

        return value;
    }

    public long readVarLong() {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = readByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public short readShort() {
        byte[] b = new byte[]{readByte(), readByte()};
        return ByteBuffer.wrap(b).getShort();
    }

    public int readInt() {
        byte[] b = new byte[]{readByte(), readByte(), readByte(), readByte()};
        return ByteBuffer.wrap(b).getInt();
    }

    public long readLong() {
        return readLong(false);
    }

    public long readLong(boolean ignoreThrow) {
        byte[] b = new byte[]{readByte(ignoreThrow), readByte(ignoreThrow), readByte(ignoreThrow), readByte(ignoreThrow), readByte(ignoreThrow), readByte(ignoreThrow), readByte(ignoreThrow), readByte(ignoreThrow)};
        return ByteBuffer.wrap(b).getLong();
    }

    public float readFloat() {
        byte[] b = new byte[]{readByte(), readByte(), readByte(), readByte()};
        return ByteBuffer.wrap(b).getFloat();
    }

    public double readDouble() {
        byte[] b = new byte[]{readByte(), readByte(), readByte(), readByte(), readByte(), readByte(), readByte(), readByte()};
        return ByteBuffer.wrap(b).getDouble();
    }

    public boolean readBoolean() {
        return readByte() != 0;
    }

    public String readString() {
        return this.readString(32767);
    }

    public String readString(int maxLength) {
        int stringLength = this.readVarInt();
        int encodedStringLength = toEncodedStringLength(maxLength);
        if (stringLength > encodedStringLength)
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + stringLength + " > " + encodedStringLength + ")");
        else if (stringLength < 0)
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        else {
            String string = this.toString(stringLength, StandardCharsets.UTF_8);
            addReaderIndex(stringLength);
            if (string.length() > maxLength)
                throw new DecoderException("The received string length is longer than maximum allowed (" + string.length() + " > " + maxLength + ")");
            else return string;
        }
    }

    public UUID readUuid() {
        return new UUID(this.readLong(), this.readLong());
    }

    public void writeBlockPos(BlockPos pos) {
        this.writeLong(pos.asLong());
    }

    public BlockPos readBlockPos() {
        return BlockPos.fromLong(readLong());
    }

    public void writeVec3d(Vec3d pos) {
        this.writeDouble(pos.x);
        this.writeDouble(pos.y);
        this.writeDouble(pos.z);
    }

    public Vec3d readVec3d() {
        return new Vec3d(this.readDouble(), this.readDouble(), this.readDouble());
    }

    public void writeVec3f(Vec3f pos) {
        this.writeFloat(pos.getX());
        this.writeFloat(pos.getY());
        this.writeFloat(pos.getZ());
    }

    public Vec3f readVec3f() {
        return new Vec3f(this.readFloat(), this.readFloat(), this.readFloat());
    }

    public void writeRotation(Rotation rotate) {
        writeFloat((float) rotate.getYaw());
        writeFloat((float) rotate.getPitch());
    }

    public byte readByte() {
        return readByte(false);
    }

    public byte readByte(boolean ignoreThrow) {
        int bufferLength = buffer.size();
        if (readerIndex >= bufferLength) {
            if (ignoreThrow) return 0;
            throw new BufferLengthException(readerIndex, bufferLength);
        }

        byte b = buffer.get(readerIndex);
        readerIndex++;
        return b;
    }

    public short readUnsignedByte() {
        return (short) (readByte() & 0xFF);
    }

    public int readUnsignedShort() {
        return readShort() & 0xFFFF;
    }

    public long readUnsignedInt() {
        return readInt() & 0xFFFFFFFFL;
    }

    public Text readText() {
        return Text.of(readString());
    }

    public void writeText(Text text) {
        writeString(text.rawJson);
    }

    public void clearBuffer() {
        buffer.clear();
        setReaderIndex(0);
    }

    public String toString() {
        return new String(getBuffer());
    }

    public String toString(Charset charset) {
        return new String(getBuffer(), charset);
    }

    public String toString(int len, Charset charset) {
        return new String(Arrays.copyOfRange(getAllBuffer(), readerIndex, readerIndex + len), charset);
    }

    public String toString(int index, int len, Charset charset) {
        return new String(Arrays.copyOfRange(getAllBuffer(), index, index + len), charset);
    }

    public JSONObject readJson() {
        return new JSONObject(readString());
    }

    public byte[] getAllBuffer() {
        return convertBuffer(buffer, 0);
    }

    public byte[] getBuffer() {
        return convertBuffer(buffer, readerIndex);
    }

    public short[] getUnsignedBuffer() {
        return convertBufferUnsigned(buffer, readerIndex);
    }

    public int getLength() {
        return buffer.size() - readerIndex;
    }

    public int allLength() {
        return buffer.size();
    }

    public void writeEnumConstant(Enum<?> constant) {
        writeVarInt(constant.ordinal());
    }

    public <T extends Enum<T>> T readEnumConstant(Class<T> enumClass) {
        return (T) ((Enum<T>[])enumClass.getEnumConstants())[this.readVarInt()];
    }

    public void writeByteEnumConstant(Enum<?> constant) {
        writeByte(constant.ordinal());
    }

    public <T extends Enum<T>> T readByteEnumConstant(Class<T> enumClass) {
        return (T) ((Enum<T>[])enumClass.getEnumConstants())[this.readByte()];
    }

    public void writeNbt(Nbt data) {
    }

    public Nbt readNbt() {
        if (this.readByte() == 0)
            return null;
        else {
            addReaderIndex(-1);

            NBTInputStream nbtStream = new NBTInputStream(new DataInputStream(new ByteArrayInputStream(getBuffer())));

            try {
                Object nbt = nbtStream.readTag();

                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                NBTOutputStream nbtOutputStream = new NBTOutputStream(new DataOutputStream(outStream));
                nbtOutputStream.writeTag(nbt);
                addReaderIndex(outStream.toByteArray().length);

                String nbtS = String.valueOf(nbt);

                try {
                    return new Nbt(nbtS);
                } catch (Exception e) {
                    return new Nbt(nbtS.replace("\"{", "{").replace("}\"", "}"));
                }

            } catch (IOException var5) {
                throw new EncoderException(var5);
            }
        }
    }

    public void writeIntArray(int[] array) {
        this.writeVarInt(array.length);
        for (int i : array)
            this.writeVarInt(i);
    }

    public int[] readIntArray() {
        return this.readIntArray(this.readableBytes());
    }

    public int[] readIntArray(int maxSize) {
        int i = this.readVarInt();
        if (i > maxSize)
            throw new DecoderException("VarIntArray with size " + i + " is bigger than allowed " + maxSize);
        else {
            int[] is = new int[i];

            for(int j = 0; j < is.length; ++j)
                is[j] = this.readVarInt();

            return is;
        }
    }

    public void writeLongArray(long[] array) {
        this.writeVarInt(array.length);
        for (long l : array)
            this.writeLong(l);
    }

    public long[] readLongArray() {
        return this.readLongArray(null);
    }

    public long[] readLongArray(long @Nullable [] toArray) {
        return this.readLongArray(toArray, this.readableBytes() / 8);
    }

    public long[] readLongArray(long @Nullable [] toArray, int maxSize) {
        int i = this.readVarInt();
        if (toArray == null || toArray.length != i) {
            if (i > maxSize)
                throw new DecoderException("LongArray with size " + i + " is bigger than allowed " + maxSize);
            toArray = new long[i];
        }

        for(int j = 0; j < toArray.length; ++j)
            toArray[j] = this.readLong();

        return toArray;
    }
    public <T, C extends Collection<T>> C readCollection(IntFunction<C> collectionFactory, PacketReader<T> reader) {
        int i = this.readVarInt();
        C collection = collectionFactory.apply(i);

        for (int j = 0; j < i; ++j)
            collection.add(reader.apply(this));

        return collection;
    }

    public <T> void writeCollection(Collection<T> collection, PacketWriter<T> writer) {
        this.writeVarInt(collection.size());
        for (T object : collection)
            writer.accept(this, object);
    }

    public ItemStack readItemStack() {
        if (!readBoolean()) return ItemStack.EMPTY;

        Item item = readEnumConstant(Item.class);
        int count = readByte();
        Nbt nbt = readNbt();

        return new ItemStack(item, count, nbt);
    }

    public void writeItemStack(ItemStack stack) {
        writeBoolean(!stack.isEmpty());
        if (stack.isEmpty()) return;
        writeEnumConstant(stack.getItem());
        writeByte(stack.getCount());
    }

    public BitSet readBitSet() {
        return BitSet.valueOf(this.readLongArray());
    }

    public void writeBitSet(BitSet bitSet) {
        this.writeLongArray(bitSet.toLongArray());
    }

    @Nullable
    public <T> T readNullable(PacketReader<T> reader) {
        if (this.readBoolean()) {
            return reader.apply(this);
        }
        return null;
    }

    public <T> void writeNullable(@Nullable T value, PacketWriter<T> writer) {
        if (value != null) {
            this.writeBoolean(true);
            writer.accept(this, value);
        } else {
            this.writeBoolean(false);
        }
    }

    public BlockHitResult readBlockHitResult() {
        BlockPos blockPos = this.readBlockPos();
        Direction direction = this.readEnumConstant(Direction.class);
        float f = this.readFloat();
        float g = this.readFloat();
        float h = this.readFloat();
        boolean bl = this.readBoolean();
        return new BlockHitResult(new Vec3d((double) blockPos.getX() + (double) f, (double) blockPos.getY() + (double) g, (double) blockPos.getZ() + (double) h), direction, blockPos, bl);
    }

    public void writeBlockHitResult(BlockHitResult hitResult) {
        BlockPos blockPos = hitResult.getBlockPos();
        this.writeBlockPos(blockPos);
        this.writeEnumConstant(hitResult.getSide());
        Vec3d vec3d = hitResult.getPos();
        this.writeFloat((float) (vec3d.x - (double) blockPos.getX()));
        this.writeFloat((float) (vec3d.y - (double) blockPos.getY()));
        this.writeFloat((float) (vec3d.z - (double) blockPos.getZ()));
        this.writeBoolean(hitResult.isInsideBlock());
    }

    public <T> void writeOptional(Optional<T> value, PacketWriter<T> writer) {
        if (value.isPresent()) {
            this.writeBoolean(true);
            writer.accept(this, value.get());
        } else this.writeBoolean(false);
    }

    public <T> Optional<T> readOptional(PacketReader<T> reader) {
        return this.readBoolean() ? Optional.of(reader.apply(this)) : Optional.empty();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public void rewrite(byte[] data) {
        clearBuffer();
        writeBytes(data);
    }

    public Instant readInstant() {
        return Instant.ofEpochMilli(this.readLong());
    }

    public void writeInstant(Instant instant) {
        this.writeLong(instant.toEpochMilli());
    }

    public void decompress() {
        this.rewrite(Flater.decompress(getBuffer()));
    }

    public void addReaderIndex(int value) {
        readerIndex += value;
    }

    public void addReaderIndex() {
        readerIndex++;
    }

    public int getReaderIndex() {
        return readerIndex;
    }

    public int readableBytes() {
        return allLength() - 1 - readerIndex;
    }

    public void setReaderIndex(int value) {
        readerIndex = value;
    }

    @FunctionalInterface
    public interface PacketReader<T> extends Function<PacketByteBuffer, T> {
        default PacketReader<Optional<T>> asOptional() {
            return (buf) -> buf.readOptional(this);
        }
    }

    @FunctionalInterface
    public interface PacketWriter<T> extends BiConsumer<PacketByteBuffer, T> {
        default PacketWriter<Optional<T>> asOptional() {
            return (buf, value) -> {
                buf.writeOptional(value, this);
            };
        }
    }
}
