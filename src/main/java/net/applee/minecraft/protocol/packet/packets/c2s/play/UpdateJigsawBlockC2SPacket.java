package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.BlockPos;

public record UpdateJigsawBlockC2SPacket(BlockPos pos, String name, String target, String pool, String finalState, String joinType) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeString(name);
        buffer.writeString(target);
        buffer.writeString(pool);
        buffer.writeString(finalState);
        buffer.writeString(joinType);
    }
}
