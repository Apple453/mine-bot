package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class CooldownUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int itemId;
    int ticks;

    public int getItemId() {
        return itemId;
    }

    public int getTicks() {
        return ticks;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onCooldownUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        itemId = buffer.readVarInt();
        ticks = buffer.readVarInt();
    }
}
