package net.applee.minecraft.world.entity.effect;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.Nbt;

import javax.annotation.Nullable;

public class EffectData {

    private static final byte AMBIENT = 0x01;
    private static final byte SHOW_PARTICLES = 0x02;
    private static final byte SHOW_ICON = 0x04;

    private final Effect effect;

    private final int amplifier;
    private final int duration;
    private boolean ambient = false;
    private boolean showParticles = false;
    private boolean showIcon = false;

    @Nullable
    Nbt factor = null;

    public EffectData(PacketByteBuffer buffer) {
        effect = buffer.readEnumConstant(Effect.class);
        amplifier = buffer.readByte();
        duration = buffer.readVarInt();

        byte flags = buffer.readByte();
        if ((flags & AMBIENT) != 0) ambient = true;
        if ((flags & SHOW_PARTICLES) != 0) showParticles = true;
        if ((flags & SHOW_ICON) != 0) showIcon = true;

        if (buffer.readBoolean()) factor = buffer.readNbt();
    }

    public Effect getEffect() {
        return effect;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public boolean isShowParticles() {
        return showParticles;
    }

    public boolean isShowIcon() {
        return showIcon;
    }

    @Nullable
    public Nbt getFactor() {
        return factor;
    }
}
