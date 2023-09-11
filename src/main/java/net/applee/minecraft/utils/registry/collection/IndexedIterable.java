package net.applee.minecraft.utils.registry.collection;

import org.jetbrains.annotations.Nullable;

public interface IndexedIterable<T> extends Iterable<T> {

    int getRawId(T value);

    @Nullable
    T get(int index);

    default T getOrThrow(int index) {
        T object = this.get(index);
        if (object == null) {
            throw new IllegalArgumentException("No value with id " + index);
        } else {
            return object;
        }
    }

    int size();
}
