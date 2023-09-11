package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.BlockPos;

public record JigsawGenerateC2SPacket(BlockPos pos, int maxDepth, boolean keepJigsaws) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeVarInt(maxDepth);
        buffer.writeBoolean(keepJigsaws);
    }
}
