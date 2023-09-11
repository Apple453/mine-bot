package net.applee.minecraft.world.chunk;

import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.ChunkPos;
import net.applee.minecraft.world.block.BlockState;
import net.applee.minecraft.world.block.blockentity.BlockEntity;
import net.applee.minecraft.world.block.blockentity.BlockEntityData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chunk {

    private final ChunkPos inWorldPosition;
    private final List<ChunkSection> sections;
    private final Map<BlockPos, BlockEntity> blockEntities;

    public Chunk(ChunkPos pos, ChunkData data) {

        this.inWorldPosition = pos;
        this.sections = new ArrayList<>();
        this.blockEntities = new HashMap<>();

        sections.addAll(data.getSections());

        for (BlockEntityData beData : data.getBlockEntitiesData()) {
            int localX = beData.unpackX();
            int localZ = beData.unpackZ();
            int height = beData.getY();

            BlockPos bePos = pos.getBlockPos(localX, height, localZ);
//            blockEntities.put(bePos, BlockEntityType.getBlockEntity(beData, bePos));
        }

    }

    public ChunkPos getPosition() {
        return inWorldPosition;
    }

    public Iterable<ChunkSection> getSections() {
        return sections;
    }

    public Map<BlockPos, BlockEntity> getBlockEntities() {
        return blockEntities;
    }

    public BlockState getBlock(BlockPos pos) {
        int sectionCount = getSectionCount(pos.getY());
        if (sectionCount < 0 || sectionCount > sections.size() - 1) return null;
        BlockPos localPos = getSectionLocalPos(pos);
        return sections.get(sectionCount).get(localPos);
    }

    public void updateBlock(BlockPos pos, BlockState block) {
        int sectionCount = getSectionCount(pos.getY());
        if (sectionCount > sections.size() - 1) return;
        BlockPos localPos = getSectionLocalPos(pos);
        sections.get(sectionCount).update(localPos, block);
    }

    public int getSectionCount(int height) {
        return (height) / 16;
    }

    public BlockPos getSectionLocalPos(BlockPos pos) {
        int x = pos.getX() % 16;
        int z = pos.getZ() % 16;
        int y = (pos.getY() + 64) % 16;

        return new BlockPos(x, y, z);
    }
}
