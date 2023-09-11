package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class WorldBorderWarningBlocksChangedS2CPacket implements S2CPacket<IEventHandler> {

    int warnBlocks;

    public int getWarnBlocks() {
        return warnBlocks;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onBorderWarnBlockUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        warnBlocks = buffer.readVarInt();
    }
}
