package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record SwingHandC2SPacket(Hand hand) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeEnumConstant(hand);
    }
}
