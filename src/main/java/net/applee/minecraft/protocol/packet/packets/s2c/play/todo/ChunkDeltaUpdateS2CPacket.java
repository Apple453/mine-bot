package net.applee.minecraft.protocol.packet.packets.s2c.play.todo;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.ChunkSectionPos;
import net.applee.minecraft.world.block.BlockState;

public class ChunkDeltaUpdateS2CPacket implements S2CPacket<IEventHandler> {

    // Todo: add block data and states
    boolean noLightingUpdates;
    ChunkSectionPos sectionPos;
    short[] positions;
    BlockState[] blockStates;

    public boolean isNoLightingUpdates() {
        return noLightingUpdates;
    }

    public ChunkSectionPos getSectionPos() {
        return sectionPos;
    }

    public short[] getPositions() {
        return positions;
    }

    public BlockState[] getBlockStates() {
        return blockStates;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onChunkSectionUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
//        sectionPos = ChunkSectionPos.from(buffer.readLong());
//        noLightingUpdates = buffer.readBoolean();
//        int blockSize = buffer.readVarInt();
//        positions = new short[blockSize];
//        blockStates = new BlockState[blockSize];
//
//        for(int i = 0; i < blockSize; ++i) {
//            long block = buffer.readVarLong();
//
//            positions[i] = (short) (block & 4095L);
//            blockStates[i] = Block.STATE_IDS.get((int)(block >>> 12));
//        }

    }
}
