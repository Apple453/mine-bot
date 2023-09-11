package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.hit.BlockHitResult;

public record InteractBlockC2SPacket(Hand hand, BlockHitResult blockHitResult, int sequence) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeEnumConstant(hand);
        buffer.writeBlockHitResult(blockHitResult);
        buffer.writeVarInt(sequence);
    }
}
