package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Rotation;
import net.applee.minecraft.utils.math.Vec3i;

public abstract class EntityMoveS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Vec3i delta;
    Rotation rotation;
    boolean onGround = false;

    boolean rotated = false;
    boolean moved = false;

    public int getEntityID() {
        return entityID;
    }

    public Vec3i getDelta() {
        return delta;
    }

    public boolean isOnGround() {
        return onGround;
    }


    public Rotation getRotation() {
        return rotation;
    }

    public boolean isRotated() {
        return rotated;
    }

    public boolean isMoved() {
        return moved;
    }

    public static class Move extends EntityMoveS2CPacket {

        @Override
        public void apply(IEventHandler handler) {
            handler.onEntityMove(this);
        }

        @Override
        public void load(PacketByteBuffer buffer) {
            entityID = buffer.readVarInt();
            delta = new Vec3i(buffer.readShort(), buffer.readShort(), buffer.readShort());
            onGround = buffer.readBoolean();
            moved = true;
        }
    }

    public static class Rotate extends EntityMoveS2CPacket {

        @Override
        public void apply(IEventHandler handler) {
            handler.onEntityMove(this);
        }

        @Override
        public void load(PacketByteBuffer buffer) {
            entityID = buffer.readVarInt();
            rotation = new Rotation(buffer.readByte(), buffer.readByte());
            onGround = buffer.readBoolean();
            rotated = true;
        }
    }

    public static class RotateAndMove extends EntityMoveS2CPacket {

        @Override
        public void apply(IEventHandler handler) {
            handler.onEntityMove(this);
        }

        @Override
        public void load(PacketByteBuffer buffer) {
            entityID = buffer.readVarInt();
            delta = new Vec3i(buffer.readShort(), buffer.readShort(), buffer.readShort());
            rotation = new Rotation(buffer.readByte(), buffer.readByte());
            onGround = buffer.readBoolean();
            moved = true;
            rotated = true;
        }
    }
}
