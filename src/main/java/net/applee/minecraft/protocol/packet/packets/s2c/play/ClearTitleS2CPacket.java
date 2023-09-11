package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ClearTitleS2CPacket implements S2CPacket<IEventHandler> {

    boolean reset;

    public boolean isReset() {
        return reset;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onClearTitle(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        reset = buffer.readBoolean();
    }
}
