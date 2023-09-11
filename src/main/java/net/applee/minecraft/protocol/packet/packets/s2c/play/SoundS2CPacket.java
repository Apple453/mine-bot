package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.enums.SoundCategory;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Vec3d;

public class SoundS2CPacket implements S2CPacket<IEventHandler> {

    int soundID;
    double pitch;
    double volume;
    long seed;
    Vec3d pos;
    SoundCategory category;

    public double getPitch() {
        return pitch;
    }

    public double getVolume() {
        return volume;
    }

    public long getSeed() {
        return seed;
    }

    public int getSoundID() {
        return soundID;
    }

    public Vec3d getPos() {
        return pos;
    }

    public SoundCategory getCategory() {
        return category;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onSound(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        soundID = buffer.readVarInt();
        category = buffer.readEnumConstant(SoundCategory.class);
        pos = new Vec3d((double) buffer.readInt() / 8, (double) buffer.readInt() / 8, (double) buffer.readInt() / 8);
        volume = buffer.readFloat();
        pitch = buffer.readFloat();
        seed = buffer.readLong();
    }
}
