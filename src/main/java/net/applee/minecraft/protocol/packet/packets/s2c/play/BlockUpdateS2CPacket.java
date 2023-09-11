package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.BlockPos;

public class BlockUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int blockId;
    BlockPos pos;

    public BlockPos getPos() {
        return pos;
    }

    public int getBlockId() {
        return blockId;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        if (MinecraftClient.getInstance().settings.isChunkLoading()) {
            eventHandler.onBlockUpdate(this);
        }
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = buffer.readBlockPos();
        blockId = buffer.readVarInt();
    }
}
