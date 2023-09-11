package net.applee.minecraft.protocol.message;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record LastSeenMessageList(List<Entry> entries) {
    public static final int MAX_ENTRIES = 5;
    public static LastSeenMessageList EMPTY = new LastSeenMessageList(List.of());

    public LastSeenMessageList(PacketByteBuffer buf) {
        this((List) buf.readCollection(PacketByteBuffer.getMaxValidator(ArrayList::new, MAX_ENTRIES), Entry::new));
    }

    public void write(PacketByteBuffer buf) {
        buf.writeCollection(this.entries, (buf2, entries) -> {
            entries.write(buf2);
        });
    }

    public void write(DataOutput output) throws IOException {
        for (Entry entry : this.entries) {
            UUID uUID = entry.profileId();
            MessageSignatureData messageSignatureData = entry.lastSignature();
            output.writeByte(70);
            output.writeLong(uUID.getMostSignificantBits());
            output.writeLong(uUID.getLeastSignificantBits());
            output.write(messageSignatureData.data());
        }
    }

    public List<Entry> entries() {
        return this.entries;
    }

    public record Entry(UUID profileId, MessageSignatureData lastSignature) {
        public Entry(PacketByteBuffer buf) {
            this(buf.readUuid(), new MessageSignatureData(buf));
        }

        public void write(PacketByteBuffer buf) {
            buf.writeUuid(this.profileId);
            this.lastSignature.write(buf);
        }

        public UUID profileId() {
            return this.profileId;
        }

        public MessageSignatureData lastSignature() {
            return this.lastSignature;
        }
    }

    public record Acknowledgment(LastSeenMessageList lastSeen, Optional<Entry> lastReceived) {
        public Acknowledgment(PacketByteBuffer buf) {
            this(new LastSeenMessageList(buf), buf.readOptional(Entry::new));
        }

        public void write(PacketByteBuffer buf) {
            this.lastSeen.write(buf);
            buf.writeOptional(this.lastReceived, (buf2, lastReceived) -> {
                lastReceived.write(buf2);
            });
        }

        public LastSeenMessageList lastSeen() {
            return this.lastSeen;
        }

        public Optional<Entry> lastReceived() {
            return this.lastReceived;
        }
    }
}