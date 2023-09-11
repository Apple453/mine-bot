package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ScoreboardDisplayS2CPacket implements S2CPacket<IEventHandler> {

    String name;
    int slot;

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onScoreboardDisplay(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        slot = buffer.readByte();
        name = buffer.readString(16);

    }
}
