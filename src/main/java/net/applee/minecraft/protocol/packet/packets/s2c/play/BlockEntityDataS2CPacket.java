package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.BlockPos;

public class BlockEntityDataS2CPacket implements S2CPacket<IEventHandler> {

    int type;
    BlockPos pos;
    Nbt data;

    public BlockPos getPos() {
        return pos;
    }

    public int getType() {
        return type;
    }

    public Nbt getData() {
        return data;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onBlockEntityUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = buffer.readBlockPos();
        type = buffer.readVarInt();
        data = buffer.readNbt();
    }
}
