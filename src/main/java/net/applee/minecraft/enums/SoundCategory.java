package net.applee.minecraft.enums;

public enum SoundCategory {
    Master("master"),
    Music("music"),
    Record("record"),
    Weather("weather"),
    Block("block"),
    Hostile("hostile"),
    Neutral("neutral"),
    Player("player"),
    Ambient("ambient"),
    Voice("voice");
    final String name;

    SoundCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
