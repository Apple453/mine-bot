package net.applee.minecraft.world.entity.misc;

import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.data.TrackerTypes;

public class ItemEntity extends Entity {

	public ItemEntity() {
		dataTracker.register(TrackerTypes.ITEM_STACK, ItemStack.EMPTY);
	}

	public ItemStack getItem() {
		return dataTracker.get(8, TrackerTypes.ITEM_STACK);
	}
}
