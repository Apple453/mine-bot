package net.applee.minecraft.world.entity.projectile;

import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.data.TrackerTypes;

public class FishingHookEntity extends ProjectileEntity {

	public FishingHookEntity() {
		dataTracker.register(TrackerTypes.INTEGER, 0)
				.register(TrackerTypes.BOOLEAN, false);
	}

	public Entity getHookedEntity() {
		int i = dataTracker.get(8, TrackerTypes.INTEGER);
		return i > 0 ? mc.world.getEntity(i - 1) : null;
	}

	public boolean isCatchable() {
		return dataTracker.get(9, TrackerTypes.BOOLEAN);
	}

}
