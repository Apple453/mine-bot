package net.applee.minecraft.world.entity.vehicle.boat;

import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.Blocks;

public enum BoatType {
	OAK(Blocks.OAK_PLANKS, "oak"),
	SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
	BIRCH(Blocks.BIRCH_PLANKS, "birch"),
	JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
	ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
	DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak"),
	MANGROVE(Blocks.MANGROVE_PLANKS, "mangrove");

	private final String name;
	private final Block baseBlock;

	BoatType(Block baseBlock, String name) {
		this.name = name;
		this.baseBlock = baseBlock;
	}

	public static BoatType getType(int type) {
		BoatType[] types = BoatType.values();
		if (type < 0 || type >= types.length) {
			type = 0;
		}
		return types[type];
	}

	public static BoatType getType(String name) {
		for (int i = 0; i < values().length; ++i) {
			if (!values()[i].getName().equals(name)) continue;
			return values()[i];
		}
		return values()[0];
	}

	public String getName() {
		return this.name;
	}

	public Block getBaseBlock() {
		return this.baseBlock;
	}

	public String toString() {
		return this.name;
	}
}
