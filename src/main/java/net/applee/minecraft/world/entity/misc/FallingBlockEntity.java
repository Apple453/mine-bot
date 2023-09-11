package net.applee.minecraft.world.entity.misc;

import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.data.TrackerTypes;

public class FallingBlockEntity extends Entity {

	public FallingBlockEntity() {
		dataTracker.register(TrackerTypes.BLOCK_POS, BlockPos.ORIGIN);
	}

	public BlockPos getSpawnPos() {
		return dataTracker.get(8, TrackerTypes.BLOCK_POS);
	}
}
