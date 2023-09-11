package net.applee.minecraft.world.entity.data;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public class TrackedType<T> {

	static <T extends Enum<T>> TrackedType<T> ofEnum(Class<T> enum_) {
		return of(PacketByteBuffer::writeEnumConstant, buf -> buf.readEnumConstant(enum_));
	}

	public static <T> TrackedType<T> of(Writer<T> writer,  Reader<T> reader) {
		return new TrackedType<>(writer, reader);
	}

	private final Reader<T> reader;
	private final Writer<T> writer;

	private TrackedType(Writer<T> writer, Reader<T> reader) {
		this.reader = reader;
		this.writer = writer;
	}

	public T read(PacketByteBuffer buffer) {
		return reader.read(buffer);
	}

	public void write(PacketByteBuffer buffer, T type) {
		writer.write(buffer, type);
	}

	public interface Reader<T> {
		T read(PacketByteBuffer buffer);
	}

	public interface Writer<T> {
		void write(PacketByteBuffer buffer, T type);
	}
}
