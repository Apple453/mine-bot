package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ChatVisibilityUpdateS2CPacket implements S2CPacket<IEventHandler> {

    boolean visibly;

    public boolean isVisibly() {
        return visibly;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onChatVisibilityUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        visibly = buffer.readBoolean();
    }
}
