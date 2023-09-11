package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.Rotation;
import net.applee.minecraft.utils.math.Vec3d;

public abstract class PlayerMoveC2SPacket implements C2SPacket {

    public record Full(Vec3d pos, Rotation rotation, boolean onGround) implements C2SPacket {
        @Override
        public void writeBuffer(PacketByteBuffer buffer) {
            buffer.writeVec3d(pos);
            buffer.writeRotation(rotation);
            buffer.writeBoolean(onGround);
        }
    }

    public record Move(Vec3d pos, boolean onGround) implements C2SPacket {
        @Override
        public void writeBuffer(PacketByteBuffer buffer) {
            buffer.writeVec3d(pos);
            buffer.writeBoolean(onGround);
        }
    }

    public record Look(Rotation rotation, boolean onGround) implements C2SPacket {
        @Override
        public void writeBuffer(PacketByteBuffer buffer) {
            buffer.writeRotation(rotation);
            buffer.writeBoolean(onGround);
        }
    }

    public record Ground(boolean onGround) implements C2SPacket {
        @Override
        public void writeBuffer(PacketByteBuffer buffer) {
            buffer.writeBoolean(onGround);
        }
    }
}
