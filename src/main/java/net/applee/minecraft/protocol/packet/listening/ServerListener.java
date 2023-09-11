package net.applee.minecraft.protocol.packet.listening;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.protocol.NetworkHandler;
import net.applee.minecraft.protocol.exceptions.BufferLengthException;
import net.applee.minecraft.protocol.exceptions.PacketNotFoundException;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.protocol.packet.packets.Packets;
import net.applee.minecraft.protocol.packet.packets.s2c.play.BlockUpdateS2CPacket;
import net.applee.minecraft.protocol.packet.packets.s2c.play.ChunkDataS2CPacket;
import net.applee.minecraft.protocol.packet.packets.s2c.play.todo.ChunkDeltaUpdateS2CPacket;
import net.applee.minecraft.utils.logging.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ServerListener {
	private final PacketByteBuffer packetBuffer = new PacketByteBuffer();
	private NetworkHandler networkHandler;
	private IEventHandler eventHandler;
	private MinecraftClient mc;

	private int packetLength = 0;
	boolean terminated = false;

	public void init(NetworkHandler networkHandler, IEventHandler eventHandler) {
		this.networkHandler = networkHandler;
		this.eventHandler = eventHandler;
		this.mc = MinecraftClient.getInstance();
	}

	public void listen() {
		terminated = false;
		try {

			while (!terminated) {
				int outInt = networkHandler.serverOutputStream.read();

				if (outInt == -1) {
					networkHandler.disconnect();
					break;
				}
				byte outByte = (byte) outInt;

				try {
					receiver(outByte);
				} catch (Exception e) {
					mc.logger.stackTrace(Logger.LogLevel.ERROR, e.getStackTrace());
					packetBuffer.clearBuffer();
				}
			}
		} catch (IOException e) {
			mc.logger.critical(e.getMessage());
		}
	}

	public void terminate() {
		terminated = true;
	}

	public void receiver(byte inputByte) {
		if (packetLength == 0) {
			packetBuffer.writeByte(inputByte);
			try {
				packetLength = packetBuffer.readVarInt();
			} catch (BufferLengthException e) {
				packetBuffer.setReaderIndex(0);
			}
		} else {
			packetBuffer.writeByte(inputByte);
			if (packetBuffer.getLength() >= packetLength) {
				packetLength = 0;
				if (networkHandler.packetCompressThreshold >= 0 && packetBuffer.readVarInt() >= networkHandler.packetCompressThreshold)
					packetBuffer.decompress();
				applyPacket(packetBuffer);
				packetBuffer.clearBuffer();
			}
		}
	}

	public void applyPacket(PacketByteBuffer buffer) {
		int packetId = buffer.readVarInt();
		S2CPacket<IEventHandler> packet;
		try {
			packet = (S2CPacket<IEventHandler>) Packets.getS2CPacket(packetId, networkHandler.isPlaying).getConstructor().newInstance();
			if (!MinecraftClient.getInstance().settings.isChunkLoading() && (packet instanceof ChunkDataS2CPacket || packet instanceof ChunkDeltaUpdateS2CPacket || packet instanceof BlockUpdateS2CPacket))
				return;
		} catch (PacketNotFoundException e) {
			throw new PacketNotFoundException(e, buffer.getAllBuffer());
		} catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		eventHandler.onPacketReceived(buffer, packet);
	}

}
