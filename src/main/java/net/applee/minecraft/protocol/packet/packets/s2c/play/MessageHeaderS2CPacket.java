package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.message.MessageSignatureData;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import javax.annotation.Nullable;
import java.util.UUID;

public class MessageHeaderS2CPacket implements S2CPacket<IEventHandler> {

    UUID sender;

    @Nullable
    MessageSignatureData data;

    public UUID getSender() {
        return sender;
    }

    @Nullable
    public MessageSignatureData getData() {
        return data;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onMessageHeader(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        data = buffer.readBoolean() ? new MessageSignatureData(buffer) : null;
        sender = buffer.readUuid();
    }
}
