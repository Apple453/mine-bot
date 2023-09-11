package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class WorldBorderSizeChangedS2CPacket implements S2CPacket<IEventHandler> {

    double diameter;

    public double getDiameter() {
        return diameter;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onBorderSizeUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        diameter = buffer.readDouble();
    }
}
