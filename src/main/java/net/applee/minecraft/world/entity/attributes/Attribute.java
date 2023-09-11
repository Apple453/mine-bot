package net.applee.minecraft.world.entity.attributes;

public enum Attribute {
    MaxHealth("generic.max_health", 20, 1, 1024),
    FollowRange("generic.follow_range", 32, 0, 2048),
    KnockbackResistance("generic.knockback_resistance", 0, 0, 1),
    MovementSpeed("generic.movement_speed", 0.7, 0, 1024),
    FlyingSpeed("generic.flying_speed", 0.4, 0, 1024),
    AttackDamage("generic.attack_damage", 2, 0, 2048),
    KnockBack("generic.attack_knockback", 0, 0, 5),
    AttackSpeed("generic.attack_speed", 4, 0, 1024),
    Armor("generic.armor", 0, 0, 30),
    ArmorToughness("generic.armor_toughness", 0, 0, 20),
    Luck("generic.luck", 0, -1024, 1024),
    SpawnReinforcementsChance("zombie.spawn_reinforcements", 0, 0, 1),
    JumpStrength("horse.jump_strength", 0.7, 0, 2),
    PlayerReachDistance("generic.reachDistance", 5, 0, 1024),
    SwimmingSpeed("forge.swimSpeed", 1, 0, 1024),
    UNKNOWN("null", 0, 0, 0);

    private final String key;
    private final double defaultValue;
    private final int minValue;
    private final int maxValue;
    Attribute(String key, double defaultValue, int min, int max) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.minValue = min;
        this.maxValue = max;
    }

    public static Attribute fromKey(String key) {
        for (Attribute i : Attribute.values()) {
            if (key.endsWith(i.key)) return i;
        }
        return UNKNOWN;
    }

    public String getKey() {
        return key;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
