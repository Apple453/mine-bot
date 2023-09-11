package net.applee.minecraft.protocol.message;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import org.json.JSONObject;

import java.util.Optional;

public class NetworkTarget {

    private final int chatType;
    private final JSONObject networkName;
    private final Optional<JSONObject> targetName;

    public NetworkTarget(PacketByteBuffer buffer) {
        chatType = buffer.readVarInt();
        networkName = buffer.readJson();
        targetName = buffer.readOptional(PacketByteBuffer::readJson);
    }

    public int getChatType() {
        return chatType;
    }

    public JSONObject getNetworkName() {
        return networkName;
    }

    public Optional<JSONObject> getTargetName() {
        return targetName;
    }
}
