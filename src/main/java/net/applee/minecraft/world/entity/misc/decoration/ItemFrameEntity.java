package net.applee.minecraft.world.entity.misc.decoration;

import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.world.entity.data.TrackerTypes;

public class ItemFrameEntity extends AbstractDecorationEntity {

	public ItemFrameEntity() {

		dataTracker.register(TrackerTypes.ITEM_STACK, ItemStack.EMPTY)
				.register(TrackerTypes.INTEGER, 0);
	}

	public ItemStack getItemStack() {
		return dataTracker.get(8, TrackerTypes.ITEM_STACK);
	}

	public int getFrameRotation() {
		return dataTracker.get(9, TrackerTypes.INTEGER);
	}
}
