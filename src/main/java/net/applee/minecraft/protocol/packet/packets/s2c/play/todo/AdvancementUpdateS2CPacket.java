package net.applee.minecraft.protocol.packet.packets.s2c.play.todo;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancementUpdateS2CPacket implements S2CPacket<IEventHandler> {

    // Todo: add SLOT and finish this shit...
    boolean clear;
    String[] identifiers;
    Map<String, Advancement> advancementsMapping = new HashMap<>();
    Map<String, Progress> progressMapping = new HashMap<>();


    @Override
    public void apply(IEventHandler handler) {
        handler.onAdvancementUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
//        clear = buffer.readBoolean();
//
//        // Advancements
//        for (int i = 0; i < buffer.readVarInt(); i++)
//            advancementsMapping.put(buffer.readString(), new Advancement(buffer));
//
//        // Identifiers
//        int idSize = buffer.readVarInt();
//        identifiers = new String[idSize];
//        for (int i = 0; i < idSize; i++)
//            identifiers[i] = buffer.readString();
//
//        // Progresses
//        for (int i = 0; i < buffer.readVarInt(); i++)
//            progressMapping.put(buffer.readString(), new Progress(buffer));

    }


    public static class Advancement {
        String parentName;
        Display display;

        Map<String, Integer> criteria;
        List<List<String>> requirements = new ArrayList<>();

        public String getParentName() {
            return parentName;
        }

        public Display getDisplay() {
            return display;
        }

        public Map<String, Integer> getCriteria() {
            return criteria;
        }

        public List<List<String>> getRequirements() {
            return requirements;
        }

        public Advancement(PacketByteBuffer buffer) {
            parentName = buffer.readBoolean() ? buffer.readString() : null;
            display = buffer.readBoolean() ? new Display(buffer) : null;

            int count = buffer.readVarInt();
            for (int i = 0; i < count; i++)
                criteria.put(buffer.readString(), 0);

            List<String> req = new ArrayList<>();
            for (int i = 0; i < buffer.readVarInt(); i++) {

                for (int ii = 0; ii < buffer.readVarInt(); ii++)
                    req.add(buffer.readString());

                requirements.add(req);
                req.clear();
            }

        }


    }

    public static class Display {

        String title;
        String description;
//        Slot icon;

        public Display(PacketByteBuffer buffer) {

        }

    }

    public static class Progress {
        public Progress(PacketByteBuffer buffer) {

        }
    }

    public static class Criterion {

    }

    public enum FrameType {
        Task,
        Challenge,
        Goal
    }

}
