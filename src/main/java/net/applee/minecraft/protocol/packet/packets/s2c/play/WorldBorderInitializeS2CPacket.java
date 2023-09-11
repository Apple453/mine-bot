package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class WorldBorderInitializeS2CPacket implements S2CPacket<IEventHandler> {

    int maxRadius;
    int warningTime;
    int warningBlocks;
    double centerX;
    double centerZ;
    double size;
    double sizeLerpTarget;
    long sizeLerpTime;

    public int getMaxRadius() {
        return maxRadius;
    }

    public int getWarningTime() {
        return warningTime;
    }

    public int getWarningBlocks() {
        return warningBlocks;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterZ() {
        return centerZ;
    }

    public double getSize() {
        return size;
    }

    public double getSizeLerpTarget() {
        return sizeLerpTarget;
    }

    public long getSizeLerpTime() {
        return sizeLerpTime;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onBorderInit(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        centerX = buffer.readDouble();
        centerZ = buffer.readDouble();
        size = buffer.readDouble();
        sizeLerpTarget = buffer.readDouble();
        sizeLerpTime = buffer.readVarLong();
        maxRadius = buffer.readVarInt();
        warningBlocks = buffer.readVarInt();
        warningTime = buffer.readVarInt();
    }
}
