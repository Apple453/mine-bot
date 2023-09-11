package net.applee.minecraft.protocol.message;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.time.Instant;

public class MessageBody {

    private final String text;
    @Nullable
    private final JSONObject formattedMessage;
    private final Instant instant;
    private final long salt;
    private final LastSeenMessageList lastSeenMessages;

    public MessageBody(PacketByteBuffer buffer) {
        text = buffer.readString(256);
        if (buffer.readBoolean()) formattedMessage = buffer.readJson();
        else formattedMessage = null;
        instant = buffer.readInstant();
        salt = buffer.readLong();
        lastSeenMessages = new LastSeenMessageList(buffer);

    }

    public String getText() {
        return text;
    }

    public JSONObject getFormattedMessage() {
        return formattedMessage;
    }

    public Instant getInstant() {
        return instant;
    }

    public long getSalt() {
        return salt;
    }

    public LastSeenMessageList getLastSeenMessages() {
        return lastSeenMessages;
    }
}
