package net.applee.minecraft.world;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.item.map.MapState;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.ChunkPos;
import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.BlockState;
import net.applee.minecraft.world.block.blockentity.BlockEntity;
import net.applee.minecraft.world.chunk.Chunk;
import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.misc.ExpOrbEntity;
import net.applee.minecraft.world.entity.player.PlayerEntity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.applee.minecraft.utils.Utils.copyList;

public class World {
    private final MinecraftClient mc;

    private final Map<ChunkPos, Chunk> worldChunks;
    private final Map<Integer, Entity> worldEntities;
    private final Map<Integer, ExpOrbEntity> worldExpOrbs;
    private final Map<Integer, PlayerEntity> worldPlayers;
    private final Map<String, MapState> mapStates;

    private long worldTime = 0;
    private long worldAge = 0;

    private final List<Dimension> dimensions;
    private Dimension currentDimension;
    private Dimension deathDimension;

    public WorldProperties properties;

    public World() {
        this.mc = MinecraftClient.getInstance();
        this.properties = new WorldProperties();
        this.properties.worldBorder = new WorldProperties.Border();

        dimensions = new ArrayList<>();
        worldChunks = new ConcurrentHashMap<>();
        worldEntities = new ConcurrentHashMap<>();
        worldExpOrbs = new ConcurrentHashMap<>();
        worldPlayers = new ConcurrentHashMap<>();
        mapStates = new ConcurrentHashMap<>();
    }

    public void tick() {
//        unloadFarChunks();
        tickEntities();
    }

    private void tickEntities() {
        for (Entity entity : worldPlayers.values()) {
            entity.tick();
        }

        for (Entity entity : worldEntities.values()) {
            entity.tick();
        }

        for (Entity entity : worldExpOrbs.values()) {
            entity.tick();
        }
    }

    public void unloadChunk(ChunkPos pos) {
        if (mc.settings.isChunkLoading()) worldChunks.remove(pos);
    }

    public void addChunk(ChunkPos chunkPos, Chunk chunk) {
        if (!mc.settings.isChunkLoading()) return;
        if (mc.settings.isChunkLoading() && chunkPos.checkDistance()) worldChunks.put(chunkPos, chunk);
    }

    public BlockEntity getBlockEntity(BlockPos pos) {
        Chunk chunk = getChunk(pos);
        return chunk.getBlockEntities().get(pos);
    }

    public Iterable<BlockEntity> getBlockEntities() {
        List<BlockEntity> blockEntities = new ArrayList<>();
        Collection<Chunk> chunks = worldChunks.values();

        for (Chunk chunk : chunks)
            blockEntities.addAll(chunk.getBlockEntities().values());

        return blockEntities;
    }

    @Deprecated
    public BlockState getBlockState(int x, int y, int z) {
        if (!mc.settings.isChunkLoading()) return null;
        return getBlockState(new BlockPos(x, y, z));
    }

    @Deprecated
    public BlockState getBlockState(BlockPos pos) {
        if (!mc.settings.isChunkLoading()) return null;
        Chunk chunk = getChunk(pos);
        if (chunk == null) return null;
        return chunk.getBlock(pos);
    }

    @Deprecated
    public void updateBlock(BlockPos pos, int id) {
        if (!mc.settings.isChunkLoading()) return;
        Chunk chunk = getChunk(pos);
        if (chunk == null) return;

        chunk.updateBlock(pos, Block.getState(id));
    }

    @Deprecated
    public Chunk getChunk(BlockPos blockPos) {
        if (!mc.settings.isChunkLoading()) return null;
        ChunkPos chunkPos = new ChunkPos(blockPos);
        return getChunk(chunkPos);
    }

    @Deprecated
    public boolean isBlockOn(Block block, BlockPos pos) {
        if (!mc.settings.isChunkLoading()) return false;
        BlockState stateAt = getBlockState(pos);
        if (stateAt == null) return false;
        return stateAt.equals(block.defaultState);
    }

    @Deprecated
    public Chunk getChunk(ChunkPos pos) {
        if (!mc.settings.isChunkLoading()) return null;
        return worldChunks.get(pos);
    }

    @Deprecated
    public Iterable<Chunk> getChunks() {
        if (!mc.settings.isChunkLoading()) return new ArrayList<>();
        return worldChunks.values();
    }

    public void addEntity(int id, Entity entity) {
        if (entity instanceof PlayerEntity player) {
            worldPlayers.put(id, player);
        } else if (entity instanceof ExpOrbEntity expOrb)
            worldExpOrbs.put(id, expOrb);
        else worldEntities.put(id, entity);
    }

    public void removeEntity(int id) {
        if (worldEntities.containsKey(id)) worldEntities.remove(id);
        else if (worldPlayers.containsKey(id)) worldPlayers.remove(id);
        else worldExpOrbs.remove(id);
    }

    public Entity getEntity(int id) {
        if (worldEntities.containsKey(id)) return worldEntities.get(id);
        if (worldExpOrbs.containsKey(id)) return worldExpOrbs.get(id);
        if (worldPlayers.containsKey(id)) return worldPlayers.get(id);
        return null;
    }

    public PlayerEntity getPlayer(int id) {
        return worldPlayers.get(id);
    }

    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        entities.addAll(getWorldEntities());
        entities.addAll(getWorldPlayers());
        entities.addAll(getExpOrbs());
        return entities;
    }

    public List<Entity> getWorldEntities() {
        List<Entity> entities = new ArrayList<>();
        copyList(worldEntities.values(), entities);
        return entities;
    }

    public List<ExpOrbEntity> getExpOrbs() {
        List<ExpOrbEntity> entities = new ArrayList<>();
        copyList(worldExpOrbs.values(), entities);
        return entities;
    }

    public List<PlayerEntity> getWorldPlayers() {
        List<PlayerEntity> entities = new ArrayList<>();
        copyList(worldPlayers.values(), entities);
        return entities;
    }

    public long getTime() {
        return Math.abs(worldTime);
    }

    public boolean timeCycleLocked() {
        return worldTime < 0;
    }

    public void setTime(long worldTime) {
        this.worldTime = worldTime;
    }

    public long getAge() {
        return worldAge;
    }

    public void setAge(long worldAge) {
        this.worldAge = worldAge;
    }

    public Dimension getCurrentDimension() {
        return currentDimension;
    }

    public void setCurrentDimension(Dimension currentDimension) {
        this.currentDimension = currentDimension;
    }

    public Dimension getDeathDimension() {
        return deathDimension;
    }

    public void setDeathDimension(Dimension deathDimension) {
        this.deathDimension = deathDimension;
    }

    public void addDimension(String name) {
        dimensions.add(Dimension.getByName(name));
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void addMapState(String id, MapState state) {
        mapStates.put(id, state);
    }

    public MapState getMapState(String id) {
        return mapStates.get(id);
    }

    public Map<String, MapState> getMapStates() {
        return new HashMap<>(mapStates);
    }
}
