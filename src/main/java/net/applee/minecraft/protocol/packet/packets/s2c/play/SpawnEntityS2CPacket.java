package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Vec3d;
import net.applee.minecraft.world.entity.EntityType;

import java.util.UUID;

public class SpawnEntityS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    UUID entityUuid;
    EntityType type;
    Vec3d pos;
    Vec3d velocity;
    byte pitch;
    byte yaw;
    byte headYaw;
    int data;

    public int getEntityID() {
        return entityID;
    }

    public UUID getEntityUuid() {
        return entityUuid;
    }

    public EntityType getType() {
        return type;
    }

    public Vec3d getPos() {
        return pos;
    }

    public Vec3d getVelocity() {
        return velocity;
    }

    public int getPitch() {
        return pitch;
    }

    public int getYaw() {
        return yaw;
    }

    public int getHeadYaw() {
        return headYaw;
    }

    public int getData() {
        return data;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onSpawnEntity(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        entityUuid = buffer.readUuid();
        type = buffer.readEnumConstant(EntityType.class);
        pos = buffer.readVec3d();
        pitch = buffer.readByte();
        yaw = buffer.readByte();
        headYaw = buffer.readByte();
        data = buffer.readVarInt();
        velocity = new Vec3d(buffer.readShort(), buffer.readShort(), buffer.readShort());
    }
}
