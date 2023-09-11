package net.applee.minecraft.world.entity;

public enum DamageTypes {
    IN_FIRE("inFire", true, true, false, false, false, false, false),
    LIGHTNING_BOLT("lightningBolt"),
    ON_FIRE("onFire", true, true, false, false, false, false, false),
    LAVA("lava", false, true, false, false, false, false, false),
    HOT_FLOOR("hotFloor", false, true, false, false, false, false, false),
    IN_WALL("inWall", false),
    CRAMMING("cramming", false),
    DROWN("drown", false),
    STARVE("starve", true, false, true, false, false, false, false),
    CACTUS("cactus"),
    FALL("fall", true, false, false, true, false, false, false),
    FLY_INTO_WALL("flyIntoWall", false),
    OUT_OF_WORLD("outOfWorld", true, false, false, false, false, true, false),
    GENERIC("generic", false),
    MAGIC("magic", true, false, false, false, false, false, true),
    WITHER("wither", false),
    ANVIL("anvil", false, false, false, false, true, false, false),
    FALLING_BLOCK("fallingBlock", false, false, false, false, true, false, false),
    DRAGON_BREATH("dragonBreath", false),
    DRYOUT("dryout"),
    SWEET_BERRY_BUSH("sweetBerryBush"),
    FREEZE("freeze", true),
    FALLING_STALACTITE("fallingStalactite", false, false, false, false, true, false, false),
    STALAGMITE("stalagmite", true, false, false, false, true, false, false);
    
    private final String name;
    private final boolean bypassesArmor;
	private final boolean fire;
	private final boolean unblockable;
	private final boolean falling;
	private final boolean fallingBlock;
	private final boolean outWorld;
	private final boolean magic;

    DamageTypes(String name, boolean bypassesArmor, boolean fire, boolean unblockable, boolean falling, boolean fallingBlock, boolean outWorld, boolean magic) {
        this.name = name;
        this.bypassesArmor = bypassesArmor;
        this.fire = fire;
        this.unblockable = unblockable;
        this.falling = falling;
        this.fallingBlock = fallingBlock;
        this.outWorld = outWorld;
        this.magic = magic;
    }

    DamageTypes(String name, boolean bypassesArmor) {
        this.name = name;
        this.bypassesArmor = bypassesArmor;
        this.fire = false;
        this.unblockable = false;
        this.falling = false;
        this.fallingBlock = false;
        this.outWorld = false;
        this.magic = false;
    }

    DamageTypes(String name) {
        this.name = name;
        this.bypassesArmor = false;
        this.fire = false;
        this.unblockable = false;
        this.falling = false;
        this.fallingBlock = false;
        this.outWorld = false;
        this.magic = false;
    }

    public String getName() {
        return name;
    }

    public boolean isBypassesArmor() {
        return bypassesArmor;
    }

    public boolean isFire() {
        return fire;
    }

    public boolean isUnblockable() {
        return unblockable;
    }

    public boolean isFalling() {
        return falling;
    }

    public boolean isFallingBlock() {
        return fallingBlock;
    }

    public boolean isOutWorld() {
        return outWorld;
    }

    public boolean isMagic() {
        return magic;
    }
}
