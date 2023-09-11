package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class SubtitleUpdateS2CPacket implements S2CPacket<IEventHandler> {

    String subtitle;

    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onSubtitleTextUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        subtitle = buffer.readString();
    }
}
