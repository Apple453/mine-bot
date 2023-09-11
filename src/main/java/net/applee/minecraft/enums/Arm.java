package net.applee.minecraft.enums;

public enum Arm {
    LEFT("left"),
    RIGHT("right");

    private final String name;

    Arm(String name) {
        this.name = name;
    }

    public static Arm fromName(String name) {
        if (name.equals(LEFT.name)) return LEFT;
        if (name.equals(RIGHT.name)) return RIGHT;
        return null;
    }

    public String getName() {
        return name;
    }
}
