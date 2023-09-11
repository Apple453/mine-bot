package net.applee.minecraft.world.block.blocks;

import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.BlockSettings;

public class SkullBlock extends Block {


	public SkullBlock(Type type, BlockSettings settings) {
		super(settings);
		this.type = type;
	}

	public final Type type;

	public enum Type {
		SKELETON,
		WITHER_SKELETON,
		PLAYER,
		ZOMBIE,
		CREEPER,
		DRAGON,
	}
}
