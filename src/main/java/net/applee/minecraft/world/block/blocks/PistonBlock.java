package net.applee.minecraft.world.block.blocks;

import net.applee.minecraft.world.block.BlockSettings;

public class PistonBlock extends FacingBlock {

    private final boolean sticky;

    public PistonBlock(boolean sticky, BlockSettings settings) {
        super(settings);
        this.sticky = sticky;
    }

    public boolean isSticky() {
        return sticky;
    }
}
