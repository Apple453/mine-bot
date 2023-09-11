package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Rotation;
import net.applee.minecraft.utils.math.Vec3d;

public class EntityTeleportedS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Vec3d pos;
    Rotation rotation;
    boolean onGround;

    public int getEntityID() {
        return entityID;
    }

    public Vec3d getPos() {
        return pos;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityTeleported(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        pos = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());

        rotation = new Rotation(buffer.readByte(), buffer.readByte());
        onGround = buffer.readBoolean();
    }
}
