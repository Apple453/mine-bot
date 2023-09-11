package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ScreenShaderUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int filterID;

    public int getFilterID() {
        return filterID;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onScreenShaderUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        filterID = buffer.readVarInt();
    }
}
