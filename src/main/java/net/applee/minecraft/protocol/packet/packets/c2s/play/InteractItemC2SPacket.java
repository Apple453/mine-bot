package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record InteractItemC2SPacket(Hand hand, int sequence) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeEnumConstant(hand);
        buffer.writeVarInt(sequence);
    }
}
