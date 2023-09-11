package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.string.Text;
import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public class DeathMessageS2CPacket implements S2CPacket<IEventHandler> {

    PlayerEntity player;
    @Nullable
    Entity killer;
    Text message;

    public PlayerEntity getPlayer() {
        return player;
    }

    @Nullable
    public Entity getKiller() {
        return killer;
    }

    public Text getMessage() {
        return message;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onCombatDeath(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        player = MinecraftClient.getInstance().world.getPlayer(buffer.readVarInt());
        killer = MinecraftClient.getInstance().world.getEntity(buffer.readInt());
        message = buffer.readText();
    }
}
