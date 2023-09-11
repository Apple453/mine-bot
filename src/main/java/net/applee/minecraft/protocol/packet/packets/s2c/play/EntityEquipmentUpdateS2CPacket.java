package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.item.EquipmentSlot;
import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.HashMap;
import java.util.Map;

public class EntityEquipmentUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Map<EquipmentSlot, ItemStack> slots;

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityEquipmentUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();

        slots = new HashMap<>();
        byte i;
        do {
            i = buffer.readByte();
            EquipmentSlot equipmentSlot = EquipmentSlot.values()[i & 0x7F];
            slots.put(equipmentSlot, buffer.readItemStack());

        } while ((i & 0x80) != 0);
    }

    public int getEntityID() {
        return entityID;
    }

    public Map<EquipmentSlot, ItemStack> getSlots() {
        return slots;
    }
}
