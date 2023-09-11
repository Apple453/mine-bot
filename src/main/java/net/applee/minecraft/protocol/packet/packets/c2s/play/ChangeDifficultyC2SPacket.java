package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.enums.Difficulty;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record ChangeDifficultyC2SPacket(Difficulty difficulty) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeEnumConstant(difficulty);
    }
}
