package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.Direction;

public record PlayerActionC2SPacket(Action status, BlockPos pos, Direction direction, int sequence) implements C2SPacket {


    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeEnumConstant(status);
        buffer.writeBlockPos(pos);
        buffer.writeByteEnumConstant(direction);
        buffer.writeVarInt(sequence);
    }
    
    public enum Action {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_ITEM_WITH_OFFHAND
    }
}
