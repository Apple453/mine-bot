package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record ClientCommandC2SPacket(ClientAction action) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeEnumConstant(action);
    }

    public enum ClientAction {
        Respawn,
        GetStats
    }
}
