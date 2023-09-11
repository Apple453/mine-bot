package net.applee.minecraft.protocol.packet.packets.s2c.login;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class LoginDisconnectS2CPacket implements S2CPacket<IEventHandler> {

    String reason;

    public String getReason() {
        return reason;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onLoginDisconnect(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        reason = buffer.readString();
    }
}
