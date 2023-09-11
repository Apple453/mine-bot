package net.applee.minecraft.world.entity.player;

public record PlayerAbilities(float flySpeed, float walkSpeed, boolean invulnerable, boolean flying, boolean allowFlying,
                              boolean creativeMode, boolean allowModifyWorld) {

    public static PlayerAbilities DEFAULT = new PlayerAbilities(
            0.05f,
            0.2f,
            false,
            false,
            false,
            false,
            true
    );

}
