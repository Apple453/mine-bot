package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class CloseScreenS2CPacket implements S2CPacket<IEventHandler> {

    int windowId;

    public int getWindowId() {
        return windowId;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onCloseScreen(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        windowId = buffer.readUnsignedByte();
    }
}
