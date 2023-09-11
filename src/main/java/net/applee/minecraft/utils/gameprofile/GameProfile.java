package net.applee.minecraft.utils.gameprofile;

import net.applee.minecraft.enums.GameMode;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import javax.annotation.Nullable;
import java.util.UUID;

public class GameProfile {

    private UUID uuid;
    private String name;
    private ProfileProperty[] properties;
    private GameMode gamemode;
    private int ping;
    @Nullable
    private String displayName;
    @Nullable
    private ProfileSignature signature;

    public GameProfile(UUID uuid, String name, ProfileProperty[] properties, GameMode gamemode, int ping, @Nullable String displayName, @Nullable ProfileSignature signature) {
        this.uuid = uuid;
        this.name = name;
        this.properties = properties;
        this.gamemode = gamemode;
        this.ping = ping;
        this.displayName = displayName;
        this.signature = signature;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ProfileProperty[] getProperties() {
        return properties;
    }

    public GameMode getGamemode() {
        return gamemode;
    }

    public int getPing() {
        return ping;
    }

    @Nullable
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    public ProfileSignature getSignature() {
        return signature;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public void setDisplayName(@Nullable String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "GameProfile{" +
                "name='" + name + '\'' +
                ", gamemode=" + gamemode +
                ", ping=" + ping +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    public static class ProfileProperty {
        String name;
        String value;
        @Nullable
        String signature = null;

        public ProfileProperty(PacketByteBuffer buffer) {
            name = buffer.readString();
            value = buffer.readString();
            if (buffer.readBoolean()) signature = buffer.readString();
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        @Nullable
        public String getSignature() {
            return signature;
        }

        @Override
        public String toString() {
            return "ProfileProperty{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", signature='" + signature + '\'' +
                    '}';
        }
    }

    public static class ProfileSignature {

        public ProfileSignature(PacketByteBuffer buffer) {

        }

    }
}
