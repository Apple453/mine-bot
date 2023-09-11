package net.applee.minecraft.utils;

public enum ActionResult {
	SUCCESS,
	CONSUME,
	CONSUME_PARTIAL,
	PASS,
	FAIL;

	public static ActionResult success(boolean swingHand) {
		return swingHand ? SUCCESS : CONSUME;
	}

	public boolean isAccepted() {
		return this == SUCCESS || this == CONSUME || this == CONSUME_PARTIAL;
	}

	public boolean shouldSwingHand() {
		return this == SUCCESS;
	}

	public boolean shouldIncrementStat() {
		return this == SUCCESS || this == CONSUME;
	}
}
