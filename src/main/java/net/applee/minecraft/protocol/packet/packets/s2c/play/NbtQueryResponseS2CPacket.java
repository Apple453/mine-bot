package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.Nbt;

public class NbtQueryResponseS2CPacket implements S2CPacket<IEventHandler> {

    int transactionId;
    Nbt nbtData;

    public int getTransactionId() {
        return transactionId;
    }

    public Nbt getNbtData() {
        return nbtData;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onTagQueryResponse(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        transactionId = buffer.readVarInt();
        nbtData = buffer.readNbt();
    }
}
