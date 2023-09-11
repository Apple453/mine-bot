package net.applee.minecraft.protocol.packet.packets.s2c.login;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class LoginRequestS2CPacket implements S2CPacket<IEventHandler> {

    String serverId;
    byte[] publicKey;
    byte[] verifyToken;

    public String getServerId() {
        return serverId;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onLoginEncryptRequest(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        serverId = buffer.readString(20);
        publicKey = buffer.readByteArray();
        verifyToken = buffer.readByteArray();
    }
}
