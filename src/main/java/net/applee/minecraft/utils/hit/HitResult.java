package net.applee.minecraft.utils.hit;

import net.applee.minecraft.utils.math.Vec3d;
import net.applee.minecraft.world.entity.Entity;

public abstract class HitResult {
    protected final Vec3d pos;

    protected HitResult(Vec3d pos) {
        this.pos = pos;
    }

    public double squaredDistanceTo(Entity entity) {
        double d = this.pos.x - entity.getX();
        double e = this.pos.y - entity.getY();
        double f = this.pos.z - entity.getZ();
        return d * d + e * e + f * f;
    }

    public abstract Type getType();

    public Vec3d getPos() {
        return this.pos;
    }

    public enum Type {
        MISS,
        BLOCK,
        ENTITY;
    }
}
