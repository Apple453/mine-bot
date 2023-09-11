package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.world.entity.effect.Effect;

public record UpdateBeaconEffectsC2SPacket(Effect primaryEffect, Effect secondaryEffect) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        if (primaryEffect != null && primaryEffect != Effect.None) {
            buffer.writeBoolean(true);
            buffer.writeEnumConstant(primaryEffect);
        } else buffer.writeBoolean(false);

        if (secondaryEffect != null && secondaryEffect != Effect.None) {
            buffer.writeBoolean(true);
            buffer.writeEnumConstant(secondaryEffect);
        } else buffer.writeBoolean(false);
    }
}
