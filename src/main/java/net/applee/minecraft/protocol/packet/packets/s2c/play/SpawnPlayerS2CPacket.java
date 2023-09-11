package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Rotation;
import net.applee.minecraft.utils.math.Vec3d;

import java.util.UUID;

public class SpawnPlayerS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    UUID entityUuid;
    Vec3d pos;
    Rotation rotation;

    public int getEntityID() {
        return entityID;
    }

    public UUID getEntityUuid() {
        return entityUuid;
    }

    public Vec3d getPos() {
        return pos;
    }

    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onPlayerSpawn(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        entityUuid = buffer.readUuid();
        pos = buffer.readVec3d();
        rotation = new Rotation(buffer.readByte(), buffer.readByte());
    }
}
