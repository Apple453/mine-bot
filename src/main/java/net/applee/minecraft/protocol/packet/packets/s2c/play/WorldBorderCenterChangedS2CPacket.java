package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class WorldBorderCenterChangedS2CPacket implements S2CPacket<IEventHandler> {

    double centerX;
    double centerZ;

    public double getCenterX() {
        return centerX;
    }

    public double getCenterZ() {
        return centerZ;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onBorderCenterUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        centerX = buffer.readDouble();
        centerZ = buffer.readDouble();
    }
}
