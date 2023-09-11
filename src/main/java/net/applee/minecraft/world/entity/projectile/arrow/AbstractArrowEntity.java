package net.applee.minecraft.world.entity.projectile.arrow;

import net.applee.minecraft.world.entity.data.TrackerTypes;
import net.applee.minecraft.world.entity.projectile.ProjectileEntity;

public abstract class AbstractArrowEntity extends ProjectileEntity {

	public AbstractArrowEntity() {
		dataTracker.register(TrackerTypes.BYTE, 0)
				.register(TrackerTypes.BYTE, 0);
	}

	public boolean isCritical() {
		return getFlag(8, 0);
	}

	public boolean isNoclip() {
		return getFlag(8, 1);
	}

	public int getPiercingLevel() {
		return dataTracker.get(9, TrackerTypes.BYTE);
	}
}
