package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class TabListUpdateS2CPacket implements S2CPacket<IEventHandler> {

    String header;
    String footer;


    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onTabListUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        header = buffer.readString();
        footer = buffer.readString();
    }
}
