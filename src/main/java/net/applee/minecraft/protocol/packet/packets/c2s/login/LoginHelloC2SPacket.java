package net.applee.minecraft.protocol.packet.packets.c2s.login;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.Optional;
import java.util.UUID;

public record LoginHelloC2SPacket(String name, Optional<UUID> uuid) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeString(name, 16);
        buffer.writeBoolean(uuid.isPresent());
        buffer.writeOptional(uuid, PacketByteBuffer::writeUuid);
    }
}
