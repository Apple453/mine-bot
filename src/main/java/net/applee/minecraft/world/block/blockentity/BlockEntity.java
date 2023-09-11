package net.applee.minecraft.world.block.blockentity;

import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.BlockPos;

public abstract class BlockEntity {

    private final BlockPos pos;
    private final Nbt nbtData;
    private final BlockEntityType blockEntityType;

    public BlockEntity(BlockEntityType blockEntityType, BlockPos pos, Nbt nbtData) {
        this.pos = pos;
        this.nbtData = nbtData;
        this.blockEntityType = blockEntityType;
    }

    public BlockEntity(BlockEntityData data, BlockPos pos) {
        this.pos = pos;
        this.nbtData = data.getData();
        this.blockEntityType = data.getType();
    }

    protected Nbt getNbtData() {
        return nbtData;
    }

    public BlockEntityType getType() {
        return blockEntityType;
    }

    public BlockPos getPos() {
        return pos;
    }
}
