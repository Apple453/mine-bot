package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.UUID;

public record TeleportToEntityC2SPacket(UUID entityUUID) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeUuid(entityUUID);
    }
}
