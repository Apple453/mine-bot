package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.world.entity.effect.Effect;

public class EntityEffectClearS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Effect effect;

    public int getEntityID() {
        return entityID;
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityEffectClear(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        effect = buffer.readEnumConstant(Effect.class);
    }
}
