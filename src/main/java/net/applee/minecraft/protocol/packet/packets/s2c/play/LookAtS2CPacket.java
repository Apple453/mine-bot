package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Vec3d;

public class LookAtS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    boolean entity;
    Vec3d targetPos;
    LookingAt looking;
    LookingAt entityLooking;

    public int getEntityID() {
        return entityID;
    }

    public boolean isEntity() {
        return entity;
    }

    public Vec3d getTargetPos() {
        return targetPos;
    }

    public LookingAt getLooking() {
        return looking;
    }

    public LookingAt getEntityLooking() {
        return entityLooking;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onLookAt(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        looking = buffer.readEnumConstant(LookingAt.class);
        targetPos = buffer.readVec3d();
        entity = buffer.readBoolean();
        if (entity) {
            entityID = buffer.readVarInt();
            entityLooking = buffer.readEnumConstant(LookingAt.class);
        }
    }

    public enum LookingAt {
        Feet,
        Eyes
    }
}
