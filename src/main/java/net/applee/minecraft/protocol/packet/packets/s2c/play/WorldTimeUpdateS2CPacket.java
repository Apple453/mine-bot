package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class WorldTimeUpdateS2CPacket implements S2CPacket<IEventHandler> {

    long worldAge;
    long timeOfDay;

    public long getWorldAge() {
        return worldAge;
    }

    public long getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onWorldTimeUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        worldAge = buffer.readLong();
        timeOfDay = buffer.readLong();

    }
}
