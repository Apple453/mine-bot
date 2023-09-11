package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.message.ArgumentSignatureDataMap;
import net.applee.minecraft.protocol.message.LastSeenMessageList;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.time.Instant;

public record ChatCommandC2SPacket(String command, Instant timestamp, long salt, ArgumentSignatureDataMap argumentSignatures, boolean signedPreview, LastSeenMessageList.Acknowledgment acknowledgment) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeString(this.command, 256);
        buffer.writeInstant(this.timestamp);
        buffer.writeLong(this.salt);
        this.argumentSignatures.write(buffer);
        buffer.writeBoolean(this.signedPreview);
        this.acknowledgment.write(buffer);
    }
}
