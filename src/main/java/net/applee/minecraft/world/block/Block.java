package net.applee.minecraft.world.block;


import java.util.ArrayList;
import java.util.List;

public class Block {

    public static List<BlockState> STATE_IDS = new ArrayList<>();

    public final BlockSettings settings;
    public final BlockState defaultState;

    public Block(BlockSettings settings) {
        this.settings = settings;
        this.defaultState = new BlockState(this);
    }

    public static BlockState getState(int id) {
        if (id >= STATE_IDS.size()) return null;
        return STATE_IDS.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return settings.equals(block.settings) && defaultState.equals(block.defaultState);
    }

    public BlockSettings getSettings() {
        return settings;
    }

}
