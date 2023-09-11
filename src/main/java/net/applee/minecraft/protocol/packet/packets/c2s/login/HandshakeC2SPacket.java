package net.applee.minecraft.protocol.packet.packets.c2s.login;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record HandshakeC2SPacket(int protocolVersion, String address, int port, PingState state) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeVarInt(protocolVersion);
        buffer.writeString(address);
        buffer.writeShort(port);
        buffer.writeEnumConstant(state);
    }

    public enum PingState {
        NONE,
        PING,
        PLAY
    }
}
