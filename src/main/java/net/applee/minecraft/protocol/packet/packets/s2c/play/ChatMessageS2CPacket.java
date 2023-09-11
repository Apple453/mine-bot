package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.message.NetworkTarget;
import net.applee.minecraft.protocol.message.SignedMessage;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ChatMessageS2CPacket implements S2CPacket<IEventHandler> {

    SignedMessage message;
    NetworkTarget networkTarget;

    @Override
    public void apply(IEventHandler handler) {
        handler.onChatMessage(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        message = new SignedMessage(buffer);
        networkTarget = new NetworkTarget(buffer);
    }

    public SignedMessage getMessage() {
        return message;
    }

    public NetworkTarget getNetworkTarget() {
        return networkTarget;
    }
}
