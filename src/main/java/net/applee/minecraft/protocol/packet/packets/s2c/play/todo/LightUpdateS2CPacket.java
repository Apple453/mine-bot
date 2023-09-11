package net.applee.minecraft.protocol.packet.packets.s2c.play.todo;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.ChunkPos;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class LightUpdateS2CPacket implements S2CPacket<IEventHandler> {

    // Todo: fix
    ChunkPos chunk;
    LightData lightData;

    public ChunkPos getChunk() {
        return chunk;
    }

    public LightData getLightData() {
        return lightData;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onLightUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
//        chunk = new ChunkPos(buffer.readVarInt(), buffer.readVarInt());
//        lightData = new LightData(buffer);
    }

    public static class LightData {
        private final BitSet initedSky;
        private final BitSet initedBlock;
        private final BitSet uninitedSky;
        private final BitSet uninitedBlock;
        private final List<byte[]> skyNibbles;
        private final List<byte[]> blockNibbles;
        private final boolean nonEdge;

        public BitSet getInitedSky() {
            return initedSky;
        }

        public BitSet getInitedBlock() {
            return initedBlock;
        }

        public BitSet getUninitedSky() {
            return uninitedSky;
        }

        public BitSet getUninitedBlock() {
            return uninitedBlock;
        }

        public List<byte[]> getSkyNibbles() {
            return skyNibbles;
        }

        public List<byte[]> getBlockNibbles() {
            return blockNibbles;
        }

        public boolean isNonEdge() {
            return nonEdge;
        }

        public LightData(PacketByteBuffer buffer) {
            this.nonEdge = buffer.readBoolean();
            this.initedSky = buffer.readBitSet();
            this.initedBlock = buffer.readBitSet();
            this.uninitedSky = buffer.readBitSet();
            this.uninitedBlock = buffer.readBitSet();
            this.skyNibbles = Collections.singletonList(buffer.readByteArray(2048));
            this.blockNibbles = Collections.singletonList(buffer.readByteArray(2048));
        }

        public void write(PacketByteBuffer buffer) {
            buffer.writeBoolean(this.nonEdge);
            buffer.writeBitSet(this.initedSky);
            buffer.writeBitSet(this.initedBlock);
            buffer.writeBitSet(this.uninitedSky);
            buffer.writeBitSet(this.uninitedBlock);
            buffer.writeCollection(this.skyNibbles, PacketByteBuffer::writeByteArray);
            buffer.writeCollection(this.blockNibbles, PacketByteBuffer::writeByteArray);
        }
    }
}
