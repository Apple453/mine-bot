package net.applee.minecraft.world.chunk;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public class PalettedContainer {

    private final int bits;
    private final Palette arrayPalette;
    private final long[] data;

    public PalettedContainer(PacketByteBuffer buffer) {
        bits = buffer.readUnsignedByte();

        if (bits == 0)
            arrayPalette = new EmptyPalette(buffer);
        else arrayPalette = new ArrayPalette(buffer);

        int dataLength = buffer.readVarInt();

        data = new long[dataLength];
        for (int i = 0; i < dataLength; i++) {
            data[i] = buffer.readLong();
        }
    }

    public int getBits() {
        return bits;
    }

    public Palette getPalette() {
        return arrayPalette;
    }

    public long[] getData() {
        return data;
    }
}
