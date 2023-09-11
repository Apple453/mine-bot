package net.applee.minecraft.world.chunk;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.ChunkPos;
import net.applee.minecraft.world.block.blockentity.BlockEntityData;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ChunkData {
    private static final int MAX_SECTIONS_DATA_SIZE = 2097152;

    private final int[] motionBlocking;
    private final int[] worldSurface;
    private final List<BlockEntityData> blockEntities;

    public final int[] getMotionBlocking() {
        return motionBlocking;
    }

    public final int[] getWorldSurface() {
        return worldSurface;
    }

    private final ChunkPos pos;
    private final List<ChunkSection> sections;

    public ChunkData(PacketByteBuffer buffer, ChunkPos pos) {
        this.pos = pos;
        Nbt heightNBT = buffer.readNbt();
        if (heightNBT == null)
            throw new RuntimeException("Can't read heightmap in packet for [" + pos.x + ", " + pos.z + "]");

        motionBlocking = parseHeightMap(heightNBT.getArray("MOTION_BLOCKING"));
        worldSurface = parseHeightMap(heightNBT.getArray("WORLD_SURFACE"));

        int dataSize = buffer.readVarInt(false);
        if (dataSize > MAX_SECTIONS_DATA_SIZE)
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");

        byte[] sectionsData = buffer.readByteArray();

        int beSize = buffer.readVarInt();
        blockEntities = new ArrayList<>();
        for (int i = 0; i < beSize; i++)
            blockEntities.add(new BlockEntityData(buffer));

        sections = new ArrayList<>();
        PacketByteBuffer sectionBuffer = new PacketByteBuffer(sectionsData);
        while (sectionBuffer.getBuffer().length != 0 || sections.size() < 23) {
            if (sections.size() >= 24) break;
            try {
                sections.add(new ChunkSection(sectionBuffer));
            } catch (Exception e) {
                break;
            }
        }
    }

    public ChunkPos getPos() {
        return pos;
    }

    public List<ChunkSection> getSections() {
        return sections;
    }

    public List<BlockEntityData> getBlockEntitiesData() {
        return blockEntities;
    }

    private int[] parseHeightMap(JSONArray array) {
        List<Integer> heights = new ArrayList<>();
        for (Object o : array) {
            String val = String.valueOf(o);
            val = val.substring(0, val.length() - 1);
            val = Long.toBinaryString(Long.parseLong(val));
            while (val.length() < 63) val = "0" + val;
            List<String> arr = Arrays.asList(val.split("(?<=\\G.{9})"));
            Collections.reverse(arr);
            for (String i : arr)
                heights.add(Integer.parseInt(i, 2));
        }
        for (int i = 0; i < 3; i++)
            heights.remove(heights.size() - 1);

        int[] arr = new int[heights.size()];
        for (int i = 0; i < heights.size(); i++)
            arr[i] = heights.get(i);
        return arr;
    }

}
