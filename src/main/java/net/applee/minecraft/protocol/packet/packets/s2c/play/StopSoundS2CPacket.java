package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.enums.SoundCategory;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import org.jetbrains.annotations.Nullable;

public class StopSoundS2CPacket implements S2CPacket<IEventHandler> {
    private static final int CATEGORY = 1;
    private static final int SOUND_ID = 2;

    @Nullable
    String soundId;
    @Nullable
    SoundCategory category;

    public @Nullable String getSoundId() {
        return soundId;
    }

    public @Nullable SoundCategory getCategory() {
        return category;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onStopSound(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        byte flags = buffer.readByte();

        if ((flags & CATEGORY) != 0)
            category = buffer.readEnumConstant(SoundCategory.class);
        else category = null;


        if ((flags & SOUND_ID) != 0)
            soundId = buffer.readString();
        else soundId = null;
    }
}
