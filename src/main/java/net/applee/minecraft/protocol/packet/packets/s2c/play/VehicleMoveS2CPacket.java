package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Rotation;
import net.applee.minecraft.utils.math.Vec3d;

public class VehicleMoveS2CPacket implements S2CPacket<IEventHandler> {

    Vec3d pos;
    Rotation rotation;

    public Vec3d getPos() {
        return pos;
    }

    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onVehicleMove(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        rotation = new Rotation(buffer.readFloat(), buffer.readFloat());
    }
}
