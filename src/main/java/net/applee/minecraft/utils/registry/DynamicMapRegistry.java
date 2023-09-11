package net.applee.minecraft.utils.registry;

import java.lang.reflect.Array;

public abstract class DynamicMapRegistry<K, V> {

    protected K[] keys;
    protected V[] vals;

    protected int registryIndex;

    public DynamicMapRegistry() {
        this.keys = (K[]) Array.newInstance(Object.class, 1);
        this.vals = (V[]) Array.newInstance(Object.class, 1);
        this.registryIndex = 0;
    }

    public void put(K key, V val) {
        keys[registryIndex] = key;
        vals[registryIndex] = val;
        K[] keys2 = (K[]) Array.newInstance(Object.class, keys.length + 1);
        V[] vals2 = (V[]) Array.newInstance(Object.class, vals.length + 1);
        System.arraycopy(keys, 0, keys2, 0, keys.length);
        System.arraycopy(vals, 0, vals2, 0, vals.length);
        keys = keys2;
        vals = vals2;
        registryIndex++;
    }

    public V get(K key) {
        int index = findKeyIndex(key);
        if (index == -1 || index >= vals.length) return null;
        return vals[index];
    }

    public K getId(V val) {
        int index = findValIndex(val);
        if (index == -1 || index >= keys.length) return null;
        return keys[index];
    }

    protected int findValIndex(V val) {
        for (int i = 0; i < vals.length; i++) {
            V v = vals[i];
            if (v.equals(val))
                return i;
        }
        return -1;
    }

    protected int findKeyIndex(K key) {
        for (int i = 0; i < keys.length; i++) {
            K k = keys[i];
            if (k.equals(key))
                return i;
        }
        return -1;
    }

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

}
