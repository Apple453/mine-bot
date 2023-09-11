package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.world.entity.attributes.Modifier;

import java.util.ArrayList;

public class EntityAttributesS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Property[] properties;

    public int getEntityID() {
        return entityID;
    }

    public Property[] getProperties() {
        return properties;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityAttributesUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        properties = buffer.readCollection(ArrayList::new, Property::new).toArray(Property[]::new);
    }

    public static class Property {

        String key;
        double value;
        Modifier[] modifiers;

        public String getKey() {
            return key;
        }

        public double getValue() {
            return value;
        }

        public Modifier[] getModifiers() {
            return modifiers;
        }

        public Property(PacketByteBuffer buffer) {
            key = buffer.readString();
            value = buffer.readDouble();
            modifiers = buffer.readCollection(ArrayList::new, Modifier::new).toArray(Modifier[]::new);
        }
    }


}
