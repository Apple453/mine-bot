package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.enums.PlayerCommand;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record PlayerCommandC2SPacket(int entityId, PlayerCommand command, int horseJump) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeEnumConstant(command);
        buffer.writeVarInt(horseJump);
    }
}
