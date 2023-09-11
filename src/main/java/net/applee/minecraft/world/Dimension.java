package net.applee.minecraft.world;

public enum Dimension {
    OWERWORLD("minecraft:overworld"),
    NETHER("minecraft:the_nether"),
    END("minecraft:the_end"),
    UNKNOWN("");

    private String name;

    Dimension(String name) {
        this.name = name;
    }

    public static Dimension getByName(String name) {
        if (name == null) return UNKNOWN;
        for (Dimension dimension : Dimension.values()) {
            if (dimension.getName().equals(name)) return dimension;
        }
        Dimension dimension = UNKNOWN;
        dimension.setName(name);
        return dimension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
