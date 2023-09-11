package net.applee.minecraft.protocol.message;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import org.json.JSONObject;

import java.util.Optional;

public class SignedMessage {

    private final MessageHeader header;
    private final MessageBody body;
    private final Optional<JSONObject> unsignedContent;
    private final FilterMask filter;

    public SignedMessage(PacketByteBuffer buffer) {
        header = new MessageHeader(buffer);
        body = new MessageBody(buffer);
        unsignedContent = buffer.readOptional(PacketByteBuffer::readJson);
        filter = FilterMask.readMask(buffer);
    }

    public MessageHeader getHeader() {
        return header;
    }

    public MessageBody getBody() {
        return body;
    }

    public Optional<JSONObject> getUnsignedContent() {
        return unsignedContent;
    }

    public FilterMask getFilter() {
        return filter;
    }
}
