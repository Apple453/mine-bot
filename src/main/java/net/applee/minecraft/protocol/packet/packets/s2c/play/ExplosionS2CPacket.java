package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.MathHelper;
import net.applee.minecraft.utils.math.Vec3d;
import net.applee.minecraft.utils.math.Vec3f;

import java.util.ArrayList;

public class ExplosionS2CPacket implements S2CPacket<IEventHandler> {

    float radius;
    Vec3d pos;
    Vec3f vel;
    BlockPos[] affectedBlocks;

    public float getRadius() {
        return radius;
    }

    public Vec3d getPos() {
        return pos;
    }

    public Vec3f getVel() {
        return vel;
    }

    public BlockPos[] getAffectedBlocks() {
        return affectedBlocks;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onExplosion(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = new Vec3d(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        radius = buffer.readFloat();
        affectedBlocks = buffer.readCollection(ArrayList::new, this::readAffected).toArray(BlockPos[]::new);
        vel = new Vec3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    private BlockPos readAffected(PacketByteBuffer buffer) {
        int x = buffer.readByte() + MathHelper.floor(pos.x);
        int y = buffer.readByte() + MathHelper.floor(pos.y);
        int z = buffer.readByte() + MathHelper.floor(pos.z);
        return new BlockPos(x, y, z);
    }
}
