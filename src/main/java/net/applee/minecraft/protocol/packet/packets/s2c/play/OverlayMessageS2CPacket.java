package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class OverlayMessageS2CPacket implements S2CPacket<IEventHandler> {

    String message;

    public String getMessage() {
        return message;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onOverlayMessage(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        message = buffer.readString();
    }
}
