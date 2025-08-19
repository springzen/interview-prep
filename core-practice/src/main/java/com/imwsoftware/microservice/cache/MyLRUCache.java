package com.imwsoftware.microservice.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyLRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public MyLRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
