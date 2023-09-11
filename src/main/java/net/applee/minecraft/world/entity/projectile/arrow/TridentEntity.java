package net.applee.minecraft.world.entity.projectile.arrow;

import net.applee.minecraft.world.entity.data.TrackerTypes;

public class TridentEntity extends AbstractArrowEntity {

	public TridentEntity() {
		dataTracker.register(TrackerTypes.INTEGER, 0)
				.register(TrackerTypes.BOOLEAN, false);
	}

	public int getLoyaltyLevel() {
		return dataTracker.get(10, TrackerTypes.INTEGER);
	}

	public boolean isEnchanted() {
		return dataTracker.get(11, TrackerTypes.BOOLEAN);
	}
}
