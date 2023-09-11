package net.applee.minecraft.item;

public enum EquipmentSlot {
    MAINHAND,
    OFFHAND,
    FEET,
    LEGS,
    CHEST,
    HEAD;

    public static final EquipmentSlot[] ARMOR = new EquipmentSlot[] {
            HEAD,
            CHEST,
            LEGS,
            FEET
    };
}
