package net.applee.minecraft.world.entity.misc;

import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.data.TrackerTypes;

import java.util.Optional;

public class EndCrystalEntity extends Entity {

	public EndCrystalEntity() {
		dataTracker.register(TrackerTypes.OPTIONAL_BLOCK_POS, Optional.empty())
				.register(TrackerTypes.BOOLEAN, true);
	}

	public Optional<BlockPos> getBeamPos() {
		return dataTracker.get(8, TrackerTypes.OPTIONAL_BLOCK_POS);
	}

	public boolean showBottom() {
		return dataTracker.get(8, TrackerTypes.BOOLEAN);
	}
}
