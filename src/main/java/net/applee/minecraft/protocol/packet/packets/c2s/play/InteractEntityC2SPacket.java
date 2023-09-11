package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.Vec3d;
import net.applee.minecraft.utils.math.Vec3f;
import net.applee.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class InteractEntityC2SPacket implements C2SPacket {

    int entityId;
    boolean sneaking;
    InteractType type;
    @Nullable Hand hand;
    @Nullable Vec3f pos;

    private InteractEntityC2SPacket(int entityId, boolean sneaking, InteractType type, @Nullable Hand hand, @Nullable Vec3f pos) {
        this.entityId = entityId;
        this.sneaking = sneaking;
        this.type = type;
        this.hand = hand;
        this.pos = pos;
    }

    public static InteractEntityC2SPacket attack(int entityId, boolean sneaking) {
        return new InteractEntityC2SPacket(entityId, sneaking, InteractType.ATTACK, null, null);
    }

    public static InteractEntityC2SPacket interact(Entity entity, boolean playerSneaking, Hand hand) {
        return new InteractEntityC2SPacket(entity.getId(), playerSneaking, InteractType.INTERACT, hand, null);
    }

    public static InteractEntityC2SPacket interactAt(Entity entity, boolean playerSneaking, Hand hand, Vec3d pos) {
        return new InteractEntityC2SPacket(entity.getId(), playerSneaking, InteractType.INTERACT_AT, hand, pos.toVec3f());
    }

    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeEnumConstant(type);
        if (type == InteractType.INTERACT_AT) {
            assert pos != null && hand != null;
            buffer.writeVec3f(pos);
            buffer.writeEnumConstant(hand);
        } else if (type == InteractType.INTERACT) {
            assert hand != null;
            buffer.writeEnumConstant(hand);
        }
        buffer.writeBoolean(sneaking);
    }

    public int getEntityId() {
        return entityId;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public InteractType getType() {
        return type;
    }

    @Nullable
    public Hand getHand() {
        return hand;
    }

    @Nullable
    public Vec3f getPos() {
        return pos;
    }

    public enum InteractType {
        INTERACT,
        ATTACK,
        INTERACT_AT;

        public boolean isAttacking() {
            return this == ATTACK;
        }

        public boolean isInteract() {
            return this == INTERACT;
        }

        public boolean isInteractingBlock() {
            return this == INTERACT_AT;
        }

        public boolean isInteractAny() {
            return this == INTERACT || this == INTERACT_AT;
        }
    }
}
