package net.applee.minecraft.enums;

public enum GameMode {
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    public boolean isAttackable() {
        return this == SURVIVAL || this == ADVENTURE;
    }

    public boolean isSpectator() {
        return this == SPECTATOR;
    }

    public boolean isCreative() {
        return this == CREATIVE;
    }
}
