package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.BlockPos;

public record GetBlockEntityTagC2SPacket(int transactionId, BlockPos pos) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeVarInt(transactionId);
        buffer.writeBlockPos(pos);
    }
}
