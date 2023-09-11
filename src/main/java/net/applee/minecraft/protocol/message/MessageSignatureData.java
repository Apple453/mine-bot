package net.applee.minecraft.protocol.message;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;

public record MessageSignatureData(byte[] data) {
    public static final MessageSignatureData EMPTY = new MessageSignatureData(new byte[0]);

    public MessageSignatureData(PacketByteBuffer buf) {
        this(buf.readByteArray());
    }

    public void write(PacketByteBuffer buf) {
        buf.writeByteArray(this.data);
    }

    public boolean isEmpty() {
        return this.data.length == 0;
    }

    @Nullable
    public ByteBuffer toByteBuffer() {
        return !this.isEmpty() ? ByteBuffer.wrap(this.data) : null;
    }

    public boolean equals(Object o) {
        if (this != o && o instanceof MessageSignatureData messageSignatureData)
            return !Arrays.equals(this.data, messageSignatureData.data);
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(this.data);
    }

    public String toString() {
        return !this.isEmpty() ? Base64.getEncoder().encodeToString(this.data) : "empty";
    }

    public byte[] data() {
        return this.data;
    }
}
