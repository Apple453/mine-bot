package net.applee.minecraft.world.entity.projectile.thrown;

import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.world.entity.data.TrackerTypes;
import net.applee.minecraft.world.entity.projectile.ProjectileEntity;

public abstract class ThrownEntity extends ProjectileEntity {

	public ThrownEntity() {
		dataTracker.register(TrackerTypes.ITEM_STACK, ItemStack.EMPTY);
	}

	public ItemStack getItem() {
		return dataTracker.get(8, TrackerTypes.ITEM_STACK);
	}
}
