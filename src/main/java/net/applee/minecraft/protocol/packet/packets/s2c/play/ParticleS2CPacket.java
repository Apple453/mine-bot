package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Vec3d;
import net.applee.minecraft.utils.math.Vec3f;

public class ParticleS2CPacket implements S2CPacket<IEventHandler> {

    int particleId;
    boolean longDistance;
    Vec3d pos;
    Vec3f offset;
    float maxSpeed;
    int particleCount;
    byte[] parameters;

    public int getParticleId() {
        return particleId;
    }

    public boolean isLongDistance() {
        return longDistance;
    }

    public Vec3d getPos() {
        return pos;
    }

    public Vec3f getOffset() {
        return offset;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public byte[] getParameters() {
        return parameters;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onParticle(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        particleId = buffer.readVarInt();
        longDistance = buffer.readBoolean();
        pos = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        offset = new Vec3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        maxSpeed = buffer.readFloat();
        particleCount = buffer.readInt();
        parameters = buffer.getBuffer();
    }

}
