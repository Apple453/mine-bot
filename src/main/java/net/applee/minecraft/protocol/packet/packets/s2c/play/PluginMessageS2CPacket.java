package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class PluginMessageS2CPacket implements S2CPacket<IEventHandler> {

    public static final String BRAND = "minecraft:brand";

    String channel;
    byte[] data;

    public String getChannel() {
        return channel;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onPluginMessage(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        channel = buffer.readString();
        data = buffer.getBuffer();
    }
}
