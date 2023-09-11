package net.applee.minecraft.protocol.packet.packets.s2c.login;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class LoginCompressionS2CPacket implements S2CPacket<IEventHandler> {

    int compressThreshold;

    public int getCompressThreshold() {
        return compressThreshold;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onLoginCompressionSet(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        compressThreshold = buffer.readVarInt();
    }
}
