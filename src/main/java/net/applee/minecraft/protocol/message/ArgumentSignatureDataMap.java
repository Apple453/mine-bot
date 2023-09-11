package net.applee.minecraft.protocol.message;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.Iterator;
import java.util.List;

public record ArgumentSignatureDataMap(List<Entry> entries) {
    public static final ArgumentSignatureDataMap EMPTY = new ArgumentSignatureDataMap(List.of());
    private static final int MAX_ARGUMENT_NAME_LENGTH = 16;

    public MessageSignatureData get(String argumentName) {
        Iterator<Entry> var2 = this.entries.iterator();

        Entry entry;
        do {
            if (!var2.hasNext()) {
                return MessageSignatureData.EMPTY;
            }

            entry = var2.next();
        } while (!entry.name.equals(argumentName));

        return entry.signature;
    }

    public void write(PacketByteBuffer buf) {
        buf.writeCollection(this.entries, (buf2, entry) -> {
            entry.write(buf2);
        });
    }

    @FunctionalInterface
    public interface ArgumentSigner {
        MessageSignatureData sign(String argumentName, String value);
    }

    public record Entry(String name, MessageSignatureData signature) {

        public Entry(PacketByteBuffer buf) {
            this(buf.readString(MAX_ARGUMENT_NAME_LENGTH), new MessageSignatureData(buf));
        }

        public void write(PacketByteBuffer buf) {
            buf.writeString(this.name, MAX_ARGUMENT_NAME_LENGTH);
            this.signature.write(buf);
        }

        public String name() {
            return this.name;
        }

        public MessageSignatureData signature() {
            return this.signature;
        }
    }
}