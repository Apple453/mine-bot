package net.applee.minecraft.enums;

public enum Hand {
    MAIN_HAND("main_hand"),
    OFF_HAND("off_hand");

    private final String name;

    Hand(String name) {
        this.name = name;
    }

    public static Hand fromName(String name) {
        if (name.equals(MAIN_HAND.name)) return MAIN_HAND;
        if (name.equals(OFF_HAND.name)) return OFF_HAND;
        return null;
    }

    public String getName() {
        return name;
    }
}
