package net.applee.minecraft.world.entity.misc;

import net.applee.minecraft.world.entity.Entity;

public class ExpOrbEntity extends Entity {

    int count = -1;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
