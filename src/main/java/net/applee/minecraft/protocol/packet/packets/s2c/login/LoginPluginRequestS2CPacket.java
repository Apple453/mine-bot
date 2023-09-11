package net.applee.minecraft.protocol.packet.packets.s2c.login;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class LoginPluginRequestS2CPacket implements S2CPacket<IEventHandler> {

    int messageId;
    String channel;
    byte[] data;

    public int getMessageId() {
        return messageId;
    }

    public String getChannel() {
        return channel;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onLoginPluginRequest(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        messageId = buffer.readVarInt();
        channel = buffer.readString();
        data = buffer.getBuffer();
    }
}
