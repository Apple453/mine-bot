package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class EndCombatS2CPacket implements S2CPacket<IEventHandler> {

    int duration;
    int entityID;

    public int getDuration() {
        return duration;
    }

    public int getEntityID() {
        return entityID;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onCombatEnded(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        duration = buffer.readVarInt();
        entityID = buffer.readInt();
    }
}
