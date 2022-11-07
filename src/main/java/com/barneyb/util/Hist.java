package com.barneyb.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Hist<K> {

    private final Map<K, Long> hist = new HashMap<>();

    public Long put(K key) {
        return put(key, 1L);
    }

    public Long put(K key, long n) {
        return hist.compute(key, (k, v) -> v == null ? n : v + n);
    }

    public long get(K key) {
        return hist.getOrDefault(key, 0L);
    }

    public boolean contains(K key) {
        return hist.containsKey(key);
    }

    public Set<K> keySet() {
        return hist.keySet();
    }

}
