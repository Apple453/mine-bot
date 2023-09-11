package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.enums.SoundCategory;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class SoundFromEntityS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    int soundID;
    double pitch;
    double volume;
    SoundCategory category;

    public int getEntityID() {
        return entityID;
    }

    public int getSoundID() {
        return soundID;
    }

    public double getPitch() {
        return pitch;
    }

    public double getVolume() {
        return volume;
    }

    public SoundCategory getCategory() {
        return category;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onSoundEntity(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        soundID = buffer.readVarInt();
        category = buffer.readEnumConstant(SoundCategory.class);
        entityID = buffer.readVarInt();
        volume = buffer.readFloat();
        pitch = buffer.readFloat();
    }
}
