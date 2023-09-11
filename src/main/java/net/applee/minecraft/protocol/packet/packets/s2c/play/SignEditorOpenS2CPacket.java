package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.BlockPos;

public class SignEditorOpenS2CPacket implements S2CPacket<IEventHandler> {

    BlockPos pos;

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onSignEditorOpen(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = buffer.readBlockPos();
    }
}
