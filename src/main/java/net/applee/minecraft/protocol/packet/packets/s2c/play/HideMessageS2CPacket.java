package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.message.MessageSignatureData;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class HideMessageS2CPacket implements S2CPacket<IEventHandler> {

    MessageSignatureData data;

    public MessageSignatureData getData() {
        return data;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onHideMessage(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        data = new MessageSignatureData(buffer);
    }
}
