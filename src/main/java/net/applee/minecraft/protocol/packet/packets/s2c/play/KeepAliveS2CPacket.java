package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class KeepAliveS2CPacket implements S2CPacket<IEventHandler> {

    long keepAliveId;

    public long getKeepAliveId() {
        return keepAliveId;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onKeepAlive(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        keepAliveId = buffer.readLong();
    }
}
