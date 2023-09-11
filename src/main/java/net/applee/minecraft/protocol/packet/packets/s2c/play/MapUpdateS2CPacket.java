package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.item.map.MapIcon;
import net.applee.minecraft.item.map.MapState;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class MapUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int id;
    byte scale;
    boolean locked;
    MapIcon[] icons;
    @Nullable
    MapState.UpdateData updateData;

    @Override
    public void apply(IEventHandler handler) {
        handler.onMapUpdate(this);
    }

    public void apply(MapState mapState) {
        if (this.icons != null) {
            mapState.replaceIcons(this.icons);
        }
        if (this.updateData != null) {
            this.updateData.setColorsTo(mapState);
        }
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        id = buffer.readVarInt();
        scale = buffer.readByte();
        locked = buffer.readBoolean();
        icons = buffer.readBoolean() ? buffer.readCollection(ArrayList::new, MapIcon::new).toArray(MapIcon[]::new) : new MapIcon[0];
        short i = buffer.readUnsignedByte();
        if (i > 0) {
            short j = buffer.readUnsignedByte();
            short k = buffer.readUnsignedByte();
            short l = buffer.readUnsignedByte();
            byte[] bs = buffer.readByteArray();
            this.updateData = new MapState.UpdateData(k, l, i, j, bs);
        } else {
            this.updateData = null;
        }
    }

    public int getId() {
        return id;
    }

    public byte getScale() {
        return scale;
    }

    public boolean isLocked() {
        return locked;
    }

    public MapIcon[] getIcons() {
        return icons;
    }

    @Nullable
    public MapState.UpdateData getUpdateData() {
        return updateData;
    }
}
