package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.world.entity.effect.EffectData;

public class EntityStatusEffectS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    EffectData data;

    public int getEntityID() {
        return entityID;
    }

    public EffectData getData() {
        return data;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityEffect(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        data = new EffectData(buffer);
    }
}
