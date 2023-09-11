package net.applee.minecraft.protocol.packet;

import net.applee.minecraft.protocol.handler.IEventHandler;


public interface S2CPacket<T extends IEventHandler> {
    void apply(T handler);
    void load(PacketByteBuffer buffer);

}
