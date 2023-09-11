package net.applee.minecraft.world.entity.misc;

import net.applee.minecraft.world.ParticleType;
import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.data.TrackerTypes;

public class AreaEffectCloudEntity extends Entity {

	public AreaEffectCloudEntity() {
		dataTracker.register(TrackerTypes.FLOAT, 0.5F)
				.register(TrackerTypes.INTEGER, 0)
				.register(TrackerTypes.BOOLEAN, false)
				.register(TrackerTypes.PARTICLE, ParticleType.EFFECT);
	}

	public float getRadius() {
		return dataTracker.get(8, TrackerTypes.FLOAT);
	}

	public int getColor() {
		return dataTracker.get(9, TrackerTypes.INTEGER);
	}

	public boolean ignoreRadius() {
		return dataTracker.get(10, TrackerTypes.BOOLEAN);
	}

	public ParticleType getParticle() {
		return dataTracker.get(11, TrackerTypes.PARTICLE);
	}
}
