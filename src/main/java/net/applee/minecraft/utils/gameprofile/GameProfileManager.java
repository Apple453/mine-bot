package net.applee.minecraft.utils.gameprofile;

import net.applee.minecraft.enums.GameMode;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameProfileManager {

    private final Map<UUID, GameProfile> profiles;

    public GameProfileManager() {
        profiles = new HashMap<>();
    }

    public GameProfile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public GameProfile getProfile(String nickname) {
        for (GameProfile profile : profiles.values()) {
            if (nickname.toLowerCase().equals(profile.getName().toLowerCase())) return profile;
        }
        return null;
    }

    public Iterable<GameProfile> getProfiles() {
        return profiles.values();
    }

    public void addProfile(UUID uuid, PacketByteBuffer buffer) {
        String name = buffer.readString(16);

        int propsCount = buffer.readVarInt();
        GameProfile.ProfileProperty[] properties = new GameProfile.ProfileProperty[propsCount];
        for (int i = 0; i < propsCount; i++) {
            properties[i] = new GameProfile.ProfileProperty(buffer);
        }

        GameMode gamemode = buffer.readEnumConstant(GameMode.class);
        int ping = buffer.readVarInt();

        String displayName = buffer.readBoolean() ? buffer.readString() : null;
        GameProfile.ProfileSignature signature = buffer.readBoolean() ? new GameProfile.ProfileSignature(buffer) : null;

        profiles.put(uuid, new GameProfile(uuid, name, properties, gamemode, ping, displayName, signature));
    }

    public void updateGamemode(UUID uuid, PacketByteBuffer buffer) {
        GameProfile profile = profiles.get(uuid);
        if (profile == null) return;
        profile.setGamemode(buffer.readEnumConstant(GameMode.class));
    }

    public void updatePing(UUID uuid, PacketByteBuffer buffer) {
        GameProfile profile = profiles.get(uuid);
        if (profile == null) return;
        profile.setPing(buffer.readVarInt());
    }

    public void updateDisplayName(UUID uuid, PacketByteBuffer buffer) {
        GameProfile profile = profiles.get(uuid);
        if (profile == null) return;

        if (buffer.readBoolean())
            profile.setDisplayName(buffer.readString());
    }

    public void removeProfile(UUID uuid) {
        profiles.remove(uuid);
    }

    public void clear() {
        profiles.clear();
    }
}
