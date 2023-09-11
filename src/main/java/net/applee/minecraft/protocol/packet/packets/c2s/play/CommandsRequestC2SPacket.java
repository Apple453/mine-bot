package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record CommandsRequestC2SPacket(int transactionId, String textBehindCursor) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeVarInt(transactionId);
        buffer.writeString(textBehindCursor, 32500);
    }
}
