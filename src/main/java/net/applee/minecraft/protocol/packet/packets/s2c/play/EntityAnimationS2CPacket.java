package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class EntityAnimationS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Animation animation;

    public int getEntityID() {
        return entityID;
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onEntityAnimation(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        animation = buffer.readEnumConstant(Animation.class);
    }

    public enum Animation {
        SWING_MAIN_HAND,
        TAKING_DAMAGE,
        BED_LEAVE,
        SWING_OFFHAND,
        CRITICAL_EFFECT,
        MAGIC_CRITICAL_EFFECT
    }
}
