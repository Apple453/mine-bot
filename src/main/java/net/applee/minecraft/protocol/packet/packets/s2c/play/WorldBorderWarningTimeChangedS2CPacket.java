package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class WorldBorderWarningTimeChangedS2CPacket implements S2CPacket<IEventHandler> {

    int warnTime;

    public int getWarnTime() {
        return warnTime;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onBorderWarnTimeUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        warnTime = buffer.readVarInt();
    }
}
