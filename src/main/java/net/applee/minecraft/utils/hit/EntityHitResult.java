package net.applee.minecraft.utils.hit;

import net.applee.minecraft.utils.math.Vec3d;
import net.applee.minecraft.world.entity.Entity;

public class EntityHitResult extends HitResult {
    private final Entity entity;

    public EntityHitResult(Entity entity) {
        this(entity, entity.getPosition());
    }

    public EntityHitResult(Entity entity, Vec3d pos) {
        super(pos);
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public HitResult.Type getType() {
        return Type.ENTITY;
    }
}
