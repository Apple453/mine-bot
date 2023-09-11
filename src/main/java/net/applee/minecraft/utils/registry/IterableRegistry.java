package net.applee.minecraft.utils.registry;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class IterableRegistry<K, V> extends DynamicMapRegistry<K, V> implements Iterable<V> {


    private final ValIterator iterator = new ValIterator();

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return iterator;
    }

    private class ValIterator implements Iterator<V> {

        private int iteratorIndex;

        public ValIterator() {
            this.iteratorIndex = 0;
        }

        @Override
        public boolean hasNext() {
            if (iteratorIndex < vals.length && vals[iteratorIndex] != null)
                return true;
            iteratorIndex = 0;
            return false;
        }

        @Override
        public V next() {
            V val = vals[iteratorIndex];
            iteratorIndex++;
            return val;
        }

    }

}
