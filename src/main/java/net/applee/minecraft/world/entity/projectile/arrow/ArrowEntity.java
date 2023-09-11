package net.applee.minecraft.world.entity.projectile.arrow;

import net.applee.minecraft.world.entity.data.TrackerTypes;

public class ArrowEntity extends AbstractArrowEntity {

	public ArrowEntity() {
		dataTracker.register(TrackerTypes.INTEGER, -1);
	}

	public int getColor() {
		return dataTracker.get(10, TrackerTypes.INTEGER);
	}
}
