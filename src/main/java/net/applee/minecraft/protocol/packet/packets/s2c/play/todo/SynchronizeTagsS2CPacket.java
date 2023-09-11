package net.applee.minecraft.protocol.packet.packets.s2c.play.todo;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.HashMap;
import java.util.Map;

public class SynchronizeTagsS2CPacket implements S2CPacket<IEventHandler> {

    // Todo: fix this fucking unworking shit packet
    Map<String, Tag> groups = new HashMap<>();

    public Map<String, Tag> getGroups() {
        return groups;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onTagsUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {

//        for (int i = 0; i < buffer.readVarInt(); i++) {
//            String name = buffer.readString();
//
////            groups.put(name, new Tag(buffer));
//        }
    }

    public static class Tag {

        Map<String, int[]> tags = new HashMap<>();

        public Map<String, int[]> getTags() {
            return tags;
        }

        public Tag(PacketByteBuffer buffer) {
//            buffer.clearBuffer();

//            for (int i = 0; i < buffer.readVarInt(); i++) {
//                String name = buffer.readString();
//                int count = buffer.readVarInt();
//                int[] arr = new int[count];
//                for (int ii = 0; ii < count; ii++) {
//                    arr[ii] = buffer.readVarInt();
//                }
//                tags.put(name, arr);
//            }
        }
    }
}
