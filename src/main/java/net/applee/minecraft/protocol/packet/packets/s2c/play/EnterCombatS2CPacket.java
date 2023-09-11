package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class EnterCombatS2CPacket implements S2CPacket<IEventHandler> {
    @Override
    public void apply(IEventHandler handler) {
        handler.onCombatStart(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
    }
}
