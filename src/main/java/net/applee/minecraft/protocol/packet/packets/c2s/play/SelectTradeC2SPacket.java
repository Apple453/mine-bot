package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record SelectTradeC2SPacket(int slot) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeVarInt(slot);
    }
}
