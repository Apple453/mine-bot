package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.world.entity.player.PlayerAbilities;

public class PlayerAbilitiesS2CPacket implements S2CPacket<IEventHandler> {

    public static final int INVULNERABLE = 0x01;
    public static final int FLYING = 0x02;
    public static final int ALLOW_FLYING = 0x04;
    public static final int CREATIVE_INSTANT_BREAK = 0x08;

    PlayerAbilities abilities;

    public PlayerAbilities getAbilities() {
        return abilities;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onPlayerAbilities(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        byte flags = buffer.readByte();

        abilities = new PlayerAbilities(
                buffer.readFloat(),
                buffer.readFloat(),
                (flags & INVULNERABLE) != 0,
                (flags & FLYING) != 0,
                (flags & ALLOW_FLYING) != 0,
                (flags & CREATIVE_INSTANT_BREAK) != 0,
                true
        );

    }
}
