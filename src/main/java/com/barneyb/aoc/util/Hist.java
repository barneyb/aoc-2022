package com.barneyb.aoc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Hist<K> {

    private final Map<K, Long> hist = new HashMap<>();

    public void put(K key) {
        put(key, 1L);
    }

    public void put(K key, long n) {
        hist.compute(key, (k, v) -> v == null ? n : v + n);
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
