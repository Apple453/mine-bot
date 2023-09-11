package net.applee.minecraft.client.screenhandler;

import net.applee.minecraft.item.ItemStack;

public class Slot {

    private ItemStack stack = ItemStack.EMPTY;
    private int x;
    private int y;
    private int id;

    public ItemStack getStack() {
        return stack;
    }
}
