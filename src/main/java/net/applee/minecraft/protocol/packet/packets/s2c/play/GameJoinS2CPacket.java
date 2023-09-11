package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.BlockPos;

import java.util.ArrayList;

public class GameJoinS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    int gamemode;
    int prevGamemode;
    int maxPlayers;
    long hashedSeed;
    int viewDistance;
    int simulationDistance;
    boolean isHardcore;
    boolean debugInfo;
    boolean respawnScreen;
    boolean debug;
    boolean flat;
    boolean hasDeathPos;
    String[] dimensions;
    Nbt nbtData;
    String dimensionType;
    String dimensionName;
    String deathDimensionName;
    BlockPos deathPos;

    public int getEntityID() {
        return entityID;
    }

    public boolean isHardcore() {
        return isHardcore;
    }

    public int getGamemode() {
        return gamemode;
    }

    public int getPrevGamemode() {
        return prevGamemode;
    }

    public String[] getDimensions() {
        return dimensions;
    }

    public Nbt getNbtData() {
        return nbtData;
    }

    public String getDimensionType() {
        return dimensionType;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public long getHashedSeed() {
        return hashedSeed;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public int getSimulationDistance() {
        return simulationDistance;
    }

    public boolean isDebugInfo() {
        return debugInfo;
    }

    public boolean isRespawnScreen() {
        return respawnScreen;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isFlat() {
        return flat;
    }

    public boolean isHasDeathPos() {
        return hasDeathPos;
    }

    public String getDeathDimensionName() {
        return deathDimensionName;
    }

    public BlockPos getDeathPos() {
        return deathPos;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onGameJoin(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readInt();
        isHardcore = buffer.readBoolean();
        gamemode = buffer.readUnsignedByte();
        prevGamemode = buffer.readByte();
        dimensions = buffer.readCollection(ArrayList::new, PacketByteBuffer::readString).toArray(String[]::new);
        nbtData = buffer.readNbt();
        dimensionType = buffer.readString();
        dimensionName = buffer.readString();
        hashedSeed = buffer.readLong();
        maxPlayers = buffer.readVarInt();
        viewDistance = buffer.readVarInt();
        simulationDistance = buffer.readVarInt();
        debugInfo = buffer.readBoolean();
        respawnScreen = buffer.readBoolean();
        debug = buffer.readBoolean();
        flat = buffer.readBoolean();
        hasDeathPos = buffer.readBoolean();
        deathDimensionName = hasDeathPos ? buffer.readString() : null;
        deathPos = hasDeathPos ? buffer.readBlockPos() : null;
    }
}
