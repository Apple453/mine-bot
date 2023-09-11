package net.applee.minecraft.client;

import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.utils.InteractionUtils;
import net.applee.minecraft.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class PlayerInventory {

    public static final int ITEM_USAGE_COOLDOWN = 5;
    public static final int CRAFT_SIZE = 5;
    public static final int ARMOR_SIZE = 4;
    public static final int MAIN_SIZE = 27;
    public static final int HOTBAR_SIZE = 9;
    public static final int OFFHAND_SIZE = 1;

    public static final int CRAFT_START_INDEX = 0;
    public static final int ARMOR_START_INDEX = CRAFT_SIZE;
    public static final int MAIN_START_INDEX = ARMOR_START_INDEX + ARMOR_SIZE;
    public static final int HOTBAR_START_INDEX = MAIN_START_INDEX + MAIN_SIZE;

    public static final int INVENTORY_SIZE = ARMOR_SIZE + CRAFT_SIZE + MAIN_SIZE + HOTBAR_SIZE + OFFHAND_SIZE;

    public static final List<Integer> CRAFT_SLOTS = Utils.createList(CRAFT_SIZE, CRAFT_START_INDEX);
    public static final List<Integer> ARMOR_SLOTS = Utils.createList(ARMOR_SIZE, ARMOR_START_INDEX);
    public static final List<Integer> MAIN_SLOTS = Utils.createList(MAIN_SIZE, MAIN_START_INDEX);
    public static final List<Integer> HOTBAR_SLOTS = Utils.createList(HOTBAR_SIZE, HOTBAR_START_INDEX);
    public static final int OFFHAND_SLOT = ARMOR_SIZE + CRAFT_SIZE + MAIN_SIZE + HOTBAR_SIZE;

    public static final int NOT_FOUND = -1;

    private final List<ItemStack> inventory = Utils.createList(INVENTORY_SIZE, ItemStack.EMPTY);
    private ItemStack cursor = ItemStack.EMPTY;

    private int selectedSlot = -1;
    private int changeCount = -1;
    private int lastState = 0;

    public static boolean isValidHotbarIndex(int slot) {
        return slot >= 0 && slot < HOTBAR_SIZE;
    }

    public static boolean isValidIndex(int slot) {
        return slot >= 0 && slot < INVENTORY_SIZE;
    }

    public static boolean isInHotbar(int slot) {
        return slot >= 36 && slot < 45 || slot == 45;
    }

    public ItemStack getArmor(int slot) {
        return getSlots(ARMOR_SLOTS).get(slot);
    }

    public List<ItemStack> getArmor() {
        return getSlots(ARMOR_SLOTS);
    }

    public List<ItemStack> getInventory() {
        return getSlots(MAIN_SLOTS);
    }

    public List<ItemStack> getHotbar() {
        return getSlots(HOTBAR_SLOTS);
    }

    public ItemStack getStackInHand(Hand hand) {
        return switch (hand) {
            case MAIN_HAND -> getMainHandStack();
            case OFF_HAND -> getOffhandStack();
        };
    }

    public ItemStack getMainHandStack() {
        if (!isValidHotbarIndex(selectedSlot)) return ItemStack.EMPTY;
        return getSlot(HOTBAR_START_INDEX + selectedSlot);
    }

    public ItemStack getOffhandStack() {
        return getSlot(OFFHAND_SLOT);
    }

    public List<ItemStack> getSlots(List<Integer> slots) {
        List<ItemStack> stacks = new ArrayList<>();
        for (int slot : slots) {
            stacks.add(getSlot(slot));
        }
        return stacks;
    }

    public ItemStack getSlot(int slot) {
        return inventory.get(slot);
    }

    public void updateSlot(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    public void updateSlot(int slot, int state, ItemStack stack) {
        inventory.set(slot, stack);
        lastState = state;
    }

    public ItemStack getCursor() {
        return cursor;
    }

    public void setCursor(ItemStack cursor) {
        this.cursor = cursor;
    }

    public void updateInventory(List<ItemStack> slots, ItemStack cursor, int state) {
        Utils.rewriteList(inventory, slots);
        this.cursor = cursor;
        lastState = state;
    }

    public void setSelectedSlot(int slot) {
        selectedSlot = slot;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void updateSelectedSlot(int slot) {
        if (!isValidHotbarIndex(slot)) return;
        InteractionUtils.updateHotbarSlot(slot);
    }
}
