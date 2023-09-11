package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.UUID;

public class BossBarUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int dividers;
    short flags;
    float health;
    UUID uuid;
    Action action;
    String title;
    Color color;

    public UUID getUuid() {
        return uuid;
    }

    public Action getAction() {
        return action;
    }

    public String getTitle() {
        return title;
    }

    public float getHealth() {
        return health;
    }

    public Color getColor() {
        return color;
    }

    public int getDividers() {
        return dividers;
    }

    public short getFlags() {
        return flags;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onBossBarUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        uuid = buffer.readUuid();

        switch (buffer.readVarInt()) {
            case 0 -> {
                action = Action.Add;
                title = buffer.readString();
                health = buffer.readFloat();
                color = buffer.readEnumConstant(Color.class);
                setDividers(buffer.readVarInt());
                flags = buffer.readUnsignedByte();
            }
            case 1 -> action = Action.Remove;
            case 2 -> {
                action = Action.UpdateHealth;
                health = buffer.readFloat();
            }
            case 3 -> {
                action = Action.UpdateTitle;
                title = buffer.readString();
            }
            case 4 -> {
                action = Action.UpdateStyle;
                color = buffer.readEnumConstant(Color.class);
                setDividers(buffer.readVarInt());
            }
            case 5 -> {
                action = Action.UpdateFlags;
                flags = buffer.readUnsignedByte();
            }
        }
    }

    private void setDividers(int id) {
        dividers = switch (id) {
            case 0 -> 0;
            case 1 -> 6;
            case 2 -> 10;
            case 3 -> 12;
            case 4 -> 20;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }

    public enum Action {
        Add,
        Remove,
        UpdateHealth,
        UpdateTitle,
        UpdateStyle,
        UpdateFlags
    }

    public enum Color {
        Pink,
        Blue,
        Red,
        Green,
        Yellow,
        Purple,
        White
    }

}
