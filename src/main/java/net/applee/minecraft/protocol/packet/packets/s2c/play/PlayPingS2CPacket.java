package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class PlayPingS2CPacket implements S2CPacket<IEventHandler> {

    int id;

    public int getId() {
        return id;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onPing(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        id = buffer.readInt();
    }
}
