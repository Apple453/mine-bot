package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class BlockAcknowledgeChangeS2CPacket implements S2CPacket<IEventHandler> {

    int sequenceId;

    public int getSequenceId() {
        return sequenceId;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onAcknowledgeBlockChange(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        sequenceId = buffer.readVarInt();
    }
}
