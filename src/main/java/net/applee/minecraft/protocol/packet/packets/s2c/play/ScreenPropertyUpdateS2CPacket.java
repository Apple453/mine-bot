package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ScreenPropertyUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int windowId;
    int property;
    int value;

    public int getWindowId() {
        return windowId;
    }

    public int getProperty() {
        return property;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onScreenPropertyUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        windowId = buffer.readByte();
        property = buffer.readShort();
        value = buffer.readShort();
    }
}
