package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.BlockPos;

public class RespawnS2CPacket implements S2CPacket<IEventHandler> {

    long hashedSeed;
    int gamemode;
    int prevGamemode;
    boolean debug;
    boolean flat;
    boolean copyMetadata;
    String dimensionType;
    String dimensionName;
    String deathDimensionName = null;
    BlockPos deathPos = null;

    public long getHashedSeed() {
        return hashedSeed;
    }

    public int getGamemode() {
        return gamemode;
    }

    public int getPrevGamemode() {
        return prevGamemode;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isFlat() {
        return flat;
    }

    public boolean isCopyMetadata() {
        return copyMetadata;
    }

    public String getDimensionType() {
        return dimensionType;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public String getDeathDimensionName() {
        return deathDimensionName;
    }

    public BlockPos getDeathPos() {
        return deathPos;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onRespawn(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        dimensionType = buffer.readString();
        dimensionName = buffer.readString();
        hashedSeed = buffer.readLong();
        gamemode = buffer.readUnsignedByte();
        prevGamemode = buffer.readByte();
        debug = buffer.readBoolean();
        flat = buffer.readBoolean();
        copyMetadata = buffer.readBoolean();
        boolean hasDeathPos = buffer.readBoolean();
        if (hasDeathPos) {
            deathDimensionName = buffer.readString();
            deathPos = buffer.readBlockPos();
        }
    }
}
