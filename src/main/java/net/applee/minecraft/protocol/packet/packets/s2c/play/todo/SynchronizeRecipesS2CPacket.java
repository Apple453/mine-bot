package net.applee.minecraft.protocol.packet.packets.s2c.play.todo;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class SynchronizeRecipesS2CPacket implements S2CPacket<IEventHandler> {

    // Todo: add recipes and made packet
    @Override
    public void apply(IEventHandler handler) {
        handler.onRecipesUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {

    }
}
