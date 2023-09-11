package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.message.MessageSignatureData;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.UUID;

public record MessageAcknowledgmentC2SPacket(boolean hasData, UUID profileId, MessageSignatureData signature) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeBoolean(hasData);
        buffer.writeUuid(profileId);
        signature.write(buffer);
    }
}
