package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class TitleS2CPacket implements S2CPacket<IEventHandler> {

    String text;

    public String getText() {
        return text;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onTitleUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        text = buffer.readString();
    }
}
