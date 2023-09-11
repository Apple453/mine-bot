package net.applee.minecraft.world.entity.vehicle.boat;

import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.data.TrackerTypes;

public class BoatEntity extends Entity {

	public BoatEntity() {
		dataTracker.register(TrackerTypes.INTEGER, 0)
				.register(TrackerTypes.INTEGER, 1)
				.register(TrackerTypes.FLOAT, 0f)
				.register(TrackerTypes.INTEGER, 0)
				.register(TrackerTypes.BOOLEAN, false)
				.register(TrackerTypes.BOOLEAN, false)
				.register(TrackerTypes.INTEGER, 0);
	}

	public int getDamageWobbleTime() {
		return dataTracker.get(8, TrackerTypes.INTEGER);
	}

	public int getDamageWobbleSide() {
		return dataTracker.get(9, TrackerTypes.INTEGER);
	}

	public float getDamageWobbleStrength() {
		return dataTracker.get(10, TrackerTypes.FLOAT);
	}

	public BoatType getBoatType() {
		return BoatType.getType(dataTracker.get(11, TrackerTypes.INTEGER));
	}

	public boolean leftPaddleWorking() {
		return dataTracker.get(12, TrackerTypes.BOOLEAN);
	}

	public boolean rightPaddleWorking() {
		return dataTracker.get(13, TrackerTypes.BOOLEAN);
	}

	public int getSplashTimer() {
		return dataTracker.get(14, TrackerTypes.INTEGER);
	}
}
