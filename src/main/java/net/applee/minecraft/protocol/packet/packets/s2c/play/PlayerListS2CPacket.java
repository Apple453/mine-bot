package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.gameprofile.GameProfileManager;

import java.util.UUID;

public class PlayerListS2CPacket implements S2CPacket<IEventHandler> {

    Action action;

    public Action getAction() {
        return action;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onPlayerListUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        action = buffer.readEnumConstant(Action.class);
        int count = buffer.readVarInt();
        for (int i = 0; i < count; i++) action.read(buffer.readUuid(), buffer, MinecraftClient.getInstance().profileManager);
    }

    public enum Action {
        ADD_PLAYER {
            protected void read(UUID uuid, PacketByteBuffer buffer, GameProfileManager profileManager) {
                profileManager.addProfile(uuid, buffer);
            }
        },
        UPDATE_GAME_MODE {
            protected void read(UUID uuid, PacketByteBuffer buffer, GameProfileManager profileManager) {
                profileManager.updateGamemode(uuid, buffer);
            }
        },
        UPDATE_LATENCY {
            protected void read(UUID uuid, PacketByteBuffer buffer, GameProfileManager profileManager) {
                profileManager.updatePing(uuid, buffer);
            }
        },
        UPDATE_DISPLAY_NAME {
            protected void read(UUID uuid, PacketByteBuffer buffer, GameProfileManager profileManager) {
                profileManager.updateDisplayName(uuid, buffer);
            }
        },
        REMOVE_PLAYER {
            protected void read(UUID uuid, PacketByteBuffer buffer, GameProfileManager profileManager) {
                profileManager.removeProfile(uuid);
            }
        };

        protected abstract void read(UUID uuid, PacketByteBuffer buffer, GameProfileManager profileManager);
    }
}
