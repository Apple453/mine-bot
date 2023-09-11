package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record PlayerInputC2SPacket(double sideways, double forward, boolean jumping, boolean sneaking) implements C2SPacket {
    private static final int JUMPING_MASK = 1;
    private static final int SNEAKING_MASK = 2;

    @Override
    public void writeBuffer(PacketByteBuffer buf) {
        buf.writeFloat((float) sideways);
        buf.writeFloat((float) forward);

        byte b = 0;
        if (jumping) b = (byte) (b | 1);
        if (sneaking) b = (byte) (b | 2);
        buf.writeByte(b);
    }
}
