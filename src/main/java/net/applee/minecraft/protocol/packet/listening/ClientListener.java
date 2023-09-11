package net.applee.minecraft.protocol.packet.listening;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.protocol.NetworkHandler;
import net.applee.minecraft.protocol.exceptions.EncoderException;
import net.applee.minecraft.protocol.exceptions.PacketNotFoundException;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.packets.Packets;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static net.applee.minecraft.utils.Utils.printex;

public class ClientListener {

    private final Map<Long, C2SPacket> packetQueue = new ConcurrentHashMap<>();
    public long packetCounter = 0;
    public long lastPacket = 0;
    private final NetworkHandler networkHandler;

    public ClientListener(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }


    public void listen() {
        C2SPacket packet = packetQueue.get(lastPacket);
        if (packet == null) return;
        packetQueue.remove(lastPacket);
        lastPacket++;
        try {
            sendBuffer(encodePacket(packet));
            networkHandler.eventHandler.onPacketSent(packet);
//                prints(packet);
        } catch (EncoderException e) {
            printex(e);
        }
    }

    public PacketByteBuffer encodePacket(C2SPacket packet) {
        PacketByteBuffer buffer = new PacketByteBuffer();

        if (packet == null)
            throw new EncoderException("Packet is null");

        if (networkHandler.isPlaying) {
            int packetId = Packets.getId(packet);
            if (packetId == -1)
                throw new PacketNotFoundException(packet);

            buffer.writeVarInt(packetId);
        } else buffer.writeVarInt(0);

        packet.writeBuffer(buffer);

        int uncompressSize = buffer.allLength();

        if (networkHandler.packetCompressThreshold >= 0) {
            int compressedSize = 0;
            byte[] compressedData = buffer.getAllBuffer();
            if (uncompressSize > networkHandler.packetCompressThreshold) {
                compressedData = Flater.compress(compressedData);
                compressedSize = compressedData.length;
            }

            PacketByteBuffer compressedBuf = new PacketByteBuffer();
            compressedBuf.writeVarInt(compressedSize);
            compressedBuf.writeBytes(compressedData);
            compressedBuf.addBufferLength();

            return compressedBuf;
        }

        buffer.addBufferLength();
        return buffer;
    }

    public void addPacket(C2SPacket packet) {
        packetQueue.put(packetCounter, packet);
        packetCounter++;
    }

    private void sendBuffer(PacketByteBuffer buffer) {
        try {
            networkHandler.sendBytes(buffer.getAllBuffer());
        } catch (IOException e) {
            MinecraftClient.getInstance().logger.exception(e);
            networkHandler.disconnect();
        }
    }

}
