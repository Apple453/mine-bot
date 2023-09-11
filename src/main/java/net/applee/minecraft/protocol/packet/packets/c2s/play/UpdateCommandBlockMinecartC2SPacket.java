package net.applee.minecraft.protocol.packet.packets.c2s.play;


import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record UpdateCommandBlockMinecartC2SPacket(int entityId, String command, boolean track) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeString(command);
        buffer.writeBoolean(track);
    }
}
