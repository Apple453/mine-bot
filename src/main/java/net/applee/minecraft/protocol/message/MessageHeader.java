package net.applee.minecraft.protocol.message;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.UUID;

public class MessageHeader {

    private final MessageSignatureData signature;
    private final MessageSignatureData headerSignature;
    private final UUID senderUuid;

    public MessageHeader(PacketByteBuffer buffer) {
        if (buffer.readBoolean()) signature = new MessageSignatureData(buffer);
        else signature = MessageSignatureData.EMPTY;
        senderUuid = buffer.readUuid();
        headerSignature = new MessageSignatureData(buffer);

    }

    public MessageSignatureData getSignature() {
        return signature;
    }

    public MessageSignatureData getHeaderSignature() {
        return headerSignature;
    }

    public UUID getSenderUuid() {
        return senderUuid;
    }
}
