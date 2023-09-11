package net.applee.minecraft.world.block;

import net.applee.minecraft.enums.DyeColor;
import net.applee.minecraft.utils.math.BlockPos;

import java.util.Objects;

public class BlockSettings {
    Material material;
    MapColor mapColor;
    Block drops = null;
    boolean collidable = true;
    int luminance = 0;
    float resistance;
    float hardness;
    boolean toolRequired;
    boolean randomTicks;
    float slipperiness = 0.6f;
    float velocityMultiplier = 1.0f;
    float jumpVelocityMultiplier = 1.0f;
    boolean opaque = true;
    boolean isAir;
    boolean dynamicBounds;

    private BlockSettings(Material material, MapColor mapColor) {
        this.material = material;
        this.mapColor = mapColor;
    }

    public interface ContextPredicate {
        boolean test(BlockState state, BlockPos pos);
    }

    public interface TypedContextPredicate<A> {
        boolean test(BlockState var1, BlockPos var3, A var4);
    }

    public static BlockSettings of(Material material) {
        return of(material, material.getColor());
    }

    public static BlockSettings of(Material material, DyeColor color) {
        return of(material, color.getMapColor());
    }

    public static BlockSettings of(Material material, MapColor mapColor) {
        return new BlockSettings(material, mapColor);
    }


    public BlockSettings noCollision() {
        this.collidable = false;
        this.opaque = false;
        return this;
    }

    public BlockSettings nonOpaque() {
        this.opaque = false;
        return this;
    }

    public BlockSettings slipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
        return this;
    }

    public BlockSettings velocityMultiplier(float velocityMultiplier) {
        this.velocityMultiplier = velocityMultiplier;
        return this;
    }

    public BlockSettings luminance(int lum) {
        this.luminance = lum;
        return this;
    }

    public BlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
        this.jumpVelocityMultiplier = jumpVelocityMultiplier;
        return this;
    }

    public BlockSettings strength(float hardness, float resistance) {
        return this.hardness(hardness).resistance(resistance);
    }

    public BlockSettings breakInstantly() {
        return this.strength(0.0f);
    }

    public BlockSettings strength(float strength) {
        this.strength(strength, strength);
        return this;
    }

    public BlockSettings ticksRandomly() {
        this.randomTicks = true;
        return this;
    }

    public BlockSettings dynamicBounds() {
        this.dynamicBounds = true;
        return this;
    }

    public BlockSettings air() {
        this.isAir = true;
        return this;
    }

    public BlockSettings requiresTool() {
        this.toolRequired = true;
        return this;
    }

    public BlockSettings mapColor(MapColor color) {
        this.mapColor = color;
        return this;
    }

    public BlockSettings hardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    public BlockSettings resistance(float resistance) {
        this.resistance = Math.max(0.0f, resistance);
        return this;
    }

    public BlockSettings dropsLike(Block block) {
        drops = block;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockSettings settings = (BlockSettings) o;
        return collidable == settings.collidable && luminance == settings.luminance && Float.compare(settings.resistance, resistance) == 0 && Float.compare(settings.hardness, hardness) == 0 && toolRequired == settings.toolRequired && randomTicks == settings.randomTicks && Float.compare(settings.slipperiness, slipperiness) == 0 && Float.compare(settings.velocityMultiplier, velocityMultiplier) == 0 && Float.compare(settings.jumpVelocityMultiplier, jumpVelocityMultiplier) == 0 && opaque == settings.opaque && isAir == settings.isAir && dynamicBounds == settings.dynamicBounds && material.equals(settings.material) && mapColor.equals(settings.mapColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, mapColor, collidable, luminance, resistance, hardness, toolRequired, randomTicks, slipperiness, velocityMultiplier, jumpVelocityMultiplier, opaque, isAir, dynamicBounds);
    }

    public static BlockSettings copy(Block block) {
        BlockSettings settings = new BlockSettings(block.settings.material, block.settings.mapColor);
        settings.material = block.settings.material;
        settings.hardness = block.settings.hardness;
        settings.resistance = block.settings.resistance;
        settings.collidable = block.settings.collidable;
        settings.randomTicks = block.settings.randomTicks;
        settings.luminance = block.settings.luminance;
        settings.mapColor = block.settings.mapColor;
        settings.slipperiness = block.settings.slipperiness;
        settings.velocityMultiplier = block.settings.velocityMultiplier;
        settings.dynamicBounds = block.settings.dynamicBounds;
        settings.opaque = block.settings.opaque;
        settings.isAir = block.settings.isAir;
        settings.toolRequired = block.settings.toolRequired;
        return settings;
    }

    public Material getMaterial() {
        return material;
    }

    public MapColor getMapColor() {
        return mapColor;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public int getLuminance() {
        return luminance;
    }

    public float getResistance() {
        return resistance;
    }

    public float getHardness() {
        return hardness;
    }

    public boolean isToolRequired() {
        return toolRequired;
    }

    public boolean isRandomTicks() {
        return randomTicks;
    }

    public float getSlipperiness() {
        return slipperiness;
    }

    public float getVelocityMultiplier() {
        return velocityMultiplier;
    }

    public float getJumpVelocityMultiplier() {
        return jumpVelocityMultiplier;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public boolean isAir() {
        return isAir;
    }

    public boolean isDynamicBounds() {
        return dynamicBounds;
    }
}

