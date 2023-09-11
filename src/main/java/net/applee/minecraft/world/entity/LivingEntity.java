package net.applee.minecraft.world.entity;

import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.world.entity.data.TrackerTypes;

import java.util.Optional;

public abstract class LivingEntity extends Entity {

	public LivingEntity() {
		dataTracker.register(TrackerTypes.BYTE, (byte) 0)
				.register(TrackerTypes.FLOAT, 1F)
				.register(TrackerTypes.INTEGER, 0)
				.register(TrackerTypes.BOOLEAN, false)
				.register(TrackerTypes.INTEGER, 0)
				.register(TrackerTypes.INTEGER, 0)
				.register(TrackerTypes.OPTIONAL_BLOCK_POS, Optional.empty());
	}

	private boolean getFlag(int index) {
		return getFlag(8, index);
	}

	public boolean isUsingItem() {
		return getFlag(1);
	}

	public Hand getActiveHand() {
		return getFlag(2) ? Hand.OFF_HAND : Hand.MAIN_HAND;
	}

	public boolean isRiptideSpinAttack() {
		return getFlag(3);
	}

	public float getHealth() {
		return dataTracker.get(9, TrackerTypes.FLOAT);
	}

	public int getPotionColor() {
		return dataTracker.get(10, TrackerTypes.INTEGER);
	}

	public boolean isPotionAmbient() {
		return dataTracker.get(11, TrackerTypes.BOOLEAN);
	}

	public int getArrowsCount() {
		return dataTracker.get(12, TrackerTypes.INTEGER);
	}

	public int getBeeStingersCount() {
		return dataTracker.get(13, TrackerTypes.INTEGER);
	}

	public Optional<BlockPos> getSleepingPosition() {
		return dataTracker.get(14, TrackerTypes.OPTIONAL_BLOCK_POS);
	}

	public boolean isDead() {
		return getHealth() <= 0;
	}

	public void setHealth(float health) {
		dataTracker.set(9, health);
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	public void applyDamage(float amount) {
		this.setHealth(this.getHealth() - amount);
	}


}
