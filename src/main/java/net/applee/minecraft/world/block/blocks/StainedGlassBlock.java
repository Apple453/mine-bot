package net.applee.minecraft.world.block.blocks;

import net.applee.minecraft.enums.DyeColor;
import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.BlockSettings;

public class StainedGlassBlock extends Block {
    private final DyeColor color;

    public StainedGlassBlock(DyeColor color, BlockSettings settings) {
        super(settings);
        this.color = color;
    }

    public DyeColor getColor() {
        return this.color;
    }
}
