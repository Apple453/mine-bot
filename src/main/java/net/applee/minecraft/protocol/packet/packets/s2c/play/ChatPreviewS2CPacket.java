package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ChatPreviewS2CPacket implements S2CPacket<IEventHandler> {

    int queryId;
    boolean componentPresent;
    byte[] component;

    public int getQueryId() {
        return queryId;
    }

    public boolean isComponentPresent() {
        return componentPresent;
    }

    public byte[] getComponent() {
        return component;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onChatPreview(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        queryId = buffer.readInt();
        componentPresent = buffer.readBoolean();
        component = buffer.getBuffer();
    }
}
