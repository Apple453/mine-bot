package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import javax.annotation.Nullable;

public class SelectAdvancementTabS2CPacket implements S2CPacket<IEventHandler> {

    @Nullable
    String identifier = null;

    @Nullable
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onAdvancementTabSelect(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        if (buffer.readBoolean()) identifier = buffer.readString();
    }
}
