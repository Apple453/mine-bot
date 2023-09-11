package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.BlockPos;

import java.util.Arrays;

public record UpdateSignC2SPacket(BlockPos pos, String[] lines) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeCollection(Arrays.asList(lines), PacketByteBuffer::writeString);
    }
}
