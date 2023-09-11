package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.BlockPos;

public class PlayerSpawnPositionS2CPacket implements S2CPacket<IEventHandler> {

    BlockPos pos;
    float angle;

    public BlockPos getPos() {
        return pos;
    }

    public float getAngle() {
        return angle;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onSpawnPointUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = buffer.readBlockPos();
        angle = buffer.readFloat();
    }
}
