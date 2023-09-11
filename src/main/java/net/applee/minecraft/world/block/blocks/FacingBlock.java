package net.applee.minecraft.world.block.blocks;

import net.applee.minecraft.utils.math.Direction;
import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.BlockSettings;

public abstract class FacingBlock extends Block {

    public static Direction direction;

    public FacingBlock(BlockSettings settings) {
        super(settings);
    }
}
