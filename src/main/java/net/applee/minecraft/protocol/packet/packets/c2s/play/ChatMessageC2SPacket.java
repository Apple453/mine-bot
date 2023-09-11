package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.message.LastSeenMessageList;
import net.applee.minecraft.protocol.message.MessageSignatureData;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.time.Instant;

public record ChatMessageC2SPacket(String chatMessage, Instant timestamp, long salt, MessageSignatureData signature, boolean signedPreview, LastSeenMessageList.Acknowledgment acknowledgment) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeString(this.chatMessage, 256);
        buffer.writeInstant(this.timestamp);
        buffer.writeLong(this.salt);
        this.signature.write(buffer);
        buffer.writeBoolean(this.signedPreview);
        this.acknowledgment.write(buffer);

    }
}
