package net.applee.minecraft.protocol.packet.packets.c2s.play.test;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

@Deprecated
public record UpdateCreativeSlotC2SPacket(int slot) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeShort(slot);
    }
}
