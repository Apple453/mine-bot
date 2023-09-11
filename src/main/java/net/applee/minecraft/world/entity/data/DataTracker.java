package net.applee.minecraft.world.entity.data;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.ArrayList;
import java.util.List;

public class DataTracker {

	private final List<TrackedValue<?>> registered = new ArrayList<>();

	public static boolean isEnd(PacketByteBuffer buffer) {
		if (buffer.isEmpty()) return true;
		return (buffer.getBuffer()[0] & 0xff) == 0xff;
	}

	public <T> DataTracker register(TrackedType<T> type, Object val) {
		registered.add(new TrackedValue<>(type, (T) val));
		return this;
	}

	public <T> T get(int index, TrackedType<T> type) {
		return (T) registered.get(index).value;
	}

	public boolean read(PacketByteBuffer buffer) {
		if (isEnd(buffer)) return false;

		int index = buffer.readUnsignedByte();
		if (index >= registered.size()) return false;

		if (isEnd(buffer)) return false;

		buffer.readVarInt();
		if (isEnd(buffer)) return false;

		TrackedValue<?> value = registered.get(index);
		set(index, value.type.read(buffer));

		return true;
	}

	public void set(int index, Object value) {
		if (index >= registered.size()) return;
		registered.get(index).value = value;
	}

	public static class TrackedValue<T> {
		public TrackedType<T> type;
		public Object value;

		public TrackedValue(TrackedType<T> type, T value) {
			this.type = type;
			this.value = value;
		}
	}
}



