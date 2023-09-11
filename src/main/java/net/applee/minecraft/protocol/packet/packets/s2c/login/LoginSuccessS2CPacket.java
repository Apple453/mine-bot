package net.applee.minecraft.protocol.packet.packets.s2c.login;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.UUID;

public class LoginSuccessS2CPacket implements S2CPacket<IEventHandler> {

    UUID uuid;
    String nickname;
    Property[] properties;

    public UUID getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public Property[] getProperties() {
        return properties;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onLoginSuccess(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        uuid = buffer.readUuid();
        nickname = buffer.readString(16);

        int size = buffer.readVarInt();
        properties = new Property[size];
        for (int i = 0; i < size; i++) {
            Property prop = new Property(buffer.readString(), buffer.readString());
            if (buffer.readBoolean())
                prop.setSignature(buffer.readString());
            properties[i] = prop;
        }
    }

    public static class Property {
        String name;
        String value;
        String signature = null;

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public Property(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
