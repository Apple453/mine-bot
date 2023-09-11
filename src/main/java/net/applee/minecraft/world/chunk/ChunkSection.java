package net.applee.minecraft.world.chunk;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.string.StringUtils;
import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.BlockState;

import java.util.*;

public class ChunkSection {

    private final int blocksCount;
    private final Map<BlockPos, BlockState> blocks;
    private boolean empty = false;

    public ChunkSection(PacketByteBuffer buffer) {
        blocksCount = buffer.readShort();
        PalettedContainer blockStates = new PalettedContainer(buffer);
        PalettedContainer biomes = new PalettedContainer(buffer);

        blocks = new HashMap<>();

        int bitSize = blockStates.getBits();
        long[] statesData = blockStates.getData();
        Palette palette = blockStates.getPalette();

        int blockCounter = 0;

        if (palette instanceof EmptyPalette)
            empty = true;
        else if (palette instanceof ArrayPalette arrayPalette) {
            int[] paletteData = arrayPalette.getData();
            for (long l : statesData) {
                StringBuilder state = new StringBuilder(Long.toBinaryString(l));
                while (state.length() < 64)
                    state.insert(0, '0');

                List<String> bits = Arrays.asList(StringUtils.split(state.toString(), bitSize));
                Collections.reverse(bits);

                for (String paletteIdBinary : bits) {
                    if (paletteIdBinary.length() < bitSize) continue;

                    int paletteId = Integer.parseInt(paletteIdBinary, 2);
                    int id = paletteData[paletteId];

                    BlockState block = Block.getState(id);

//                    int a = Math.divideExact(blockCounter, 16);

                    int x = blockCounter % 16;
//                    int z = a % 16;
//                    int y = Math.divideExact(a, 16) % 16;

//                    BlockPos lPos = new BlockPos(x, y, z);

//                    blocks.put(lPos, block);
                    blockCounter++;
                }
            }
        }

    }

    public int getBlocksCount() {
        return blocksCount;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Map<BlockPos, BlockState> getBlocks() {
        return blocks;
    }

    public BlockState get(BlockPos localPos) {
        return blocks.get(localPos.abs());
    }

    public void update(BlockPos localPos, BlockState block) {
        blocks.put(localPos.abs(), block);
    }

}
