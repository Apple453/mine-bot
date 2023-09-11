package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class WorldBorderInterpolateSizeS2CPacket implements S2CPacket<IEventHandler> {

    long speed;
    double oldDiameter;
    double newDiameter;

    public long getSpeed() {
        return speed;
    }

    public double getOldDiameter() {
        return oldDiameter;
    }

    public double getNewDiameter() {
        return newDiameter;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onBorderInterpolSizeUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        oldDiameter = buffer.readDouble();
        newDiameter = buffer.readDouble();
        speed = buffer.readVarLong();
    }
}
