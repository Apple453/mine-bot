package net.applee.minecraft.world.block;

public class BlockState {

    private final int luminance;
    private final boolean isAir;
    private final Material material;
    private final MapColor mapColor;
    private final float hardness;
    private final boolean toolRequired;
    private final boolean opaque;
    private final Block owner;
    private int stateId;


    public BlockState(Block block) {
        BlockSettings settings = block.getSettings();
        this.luminance = settings.luminance;
        this.isAir = settings.isAir;
        this.material = settings.material;
        this.mapColor = settings.mapColor;
        this.hardness = settings.hardness;
        this.toolRequired = settings.toolRequired;
        this.opaque = settings.opaque;
        this.owner = block;

    }

    public int getLuminance() {
        return luminance;
    }

    public boolean isAir() {
        return isAir;
    }

    public Material getMaterial() {
        return material;
    }

    public MapColor getMapColor() {
        return mapColor;
    }

    public float getHardness() {
        return hardness;
    }

    public boolean isToolRequired() {
        return toolRequired;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public Block getBlock() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        BlockState that = (BlockState) o;

        return (stateId == that.getId()) &&
                (isAir == that.isAir) &&
                (Float.compare(that.hardness, hardness) == 0) &&
                (toolRequired == that.toolRequired) &&
                (opaque == that.opaque) &&
                material.equals(that.material) &&
                mapColor.equals(that.mapColor);
    }

    @Override
    public String toString() {
        return "BlockState{" +
                "luminance=" + luminance +
                ", isAir=" + isAir +
                ", material=" + material +
                ", mapColor=" + mapColor +
                ", hardness=" + hardness +
                ", toolRequired=" + toolRequired +
                ", opaque=" + opaque +
                ", owner=" + owner +
                '}';
    }

    public int getId() {
        return stateId;
    }

    public void setId(int stateId) {
        this.stateId = stateId;
    }
}
