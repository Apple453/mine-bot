//package net.applee.minecraft.utils.registry.collection;
//
//import org.jetbrains.annotations.Nullable;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class IdList<T> implements IndexedIterable<T> {
//    private final Object2IntMap<T> idMap;
//    private final List<T> list;
//    private int nextId;
//
//    public IdList() {
//        this(512);
//    }
//
//    public IdList(int initialSize) {
//        this.list = new ArrayList<>();
//        this.idMap = new Object2IntOpenCustomHashMap<>(initialSize, identityHashStrategy());
//        this.idMap.defaultReturnValue(-1);
//    }
//
//    public void set(T value, int id) {
//        this.idMap.put(value, id);
//        while (this.list.size() <= id) {
//            this.list.add(null);
//        }
//        this.list.set(id, value);
//        if (this.nextId <= id) {
//            this.nextId = id + 1;
//        }
//    }
//
//    public void add(T value) {
//        this.set(value, this.nextId);
//    }
//
//    public int getRawId(T value) {
//        return this.idMap.getInt(value);
//    }
//
//    @Override
//    @Nullable
//    public T get(int index) {
//        if (index >= 0 && index < this.list.size()) {
//            return this.list.get(index);
//        }
//        return null;
//    }
//
//    @Override
//    public Iterator<T> iterator() {
//        return this.list.iterator();
//    }
//
//
//    public boolean containsKey(int index) {
//        return this.get(index) != null;
//    }
//
//    @Override
//    public int size() {
//        return this.idMap.size();
//    }
//
//    public static <K> Hash.Strategy<K> identityHashStrategy() {
//        return new Hash.Strategy<>() {
//            public int hashCode(Object o) {
//                return System.identityHashCode(o);
//            }
//
//            public boolean equals(Object o, Object o2) {
//                return o == o2;
//            }
//        };
//    }
//}