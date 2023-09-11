package net.applee.minecraft.utils.registry;

import net.applee.minecraft.world.block.Block;

public class Registry {

    public static IterableRegistry<String, Block> BLOCKS = new IterableRegistry<>();


    public static Block registerBlock(String id, Block block) {
        BLOCKS.put(id, block);
        return block;
    }


}
