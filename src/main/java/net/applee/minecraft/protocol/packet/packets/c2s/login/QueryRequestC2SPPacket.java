package net.applee.minecraft.protocol.packet.packets.c2s.login;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record QueryRequestC2SPPacket() implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
    }
}
