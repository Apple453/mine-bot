package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.BlockPos;

public class BlockBreakingStageUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int stage;
    int entityId;
    BlockPos pos;

    public int getEntityId() {
        return entityId;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getStage() {
        return stage;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onBlockBreakingProgress(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityId = buffer.readVarInt();
        pos = buffer.readBlockPos();
        stage = buffer.readByte();
    }
}
