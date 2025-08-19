package com.imwsoftware.microservice.cache;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class TTLThreadSafeLRUCache<K, V> {
    private final int capacity;
    private final long ttlMillis;

    private final Map<K, CacheEntry<V>> cache = new HashMap<>();
    private final Deque<K> lruQueue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public TTLThreadSafeLRUCache(int capacity, long ttlMillis) {
        this.capacity = capacity;
        this.ttlMillis = ttlMillis;
    }

    public V get(K key) {
        lock.lock();
        try {
            CacheEntry<V> entry = cache.get(key);
            if (entry == null || isExpired(entry)) {
                cache.remove(key);
                lruQueue.remove(key);
                return null;
            }
            lruQueue.remove(key);
            lruQueue.addFirst(key);
            return entry.value;
        } finally {
            lock.unlock();
        }
    }

    public void put(K key, V value) {
        lock.lock();
        try {
            if (cache.containsKey(key)) {
                lruQueue.remove(key);
            } else if (cache.size() >= capacity) {
                evictLeastRecentlyUsed();
            }
            lruQueue.addFirst(key);
            cache.put(key, new CacheEntry<>(value));
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return cache.size();
        } finally {
            lock.unlock();
        }
    }

    public boolean containsKey(K key) {
        lock.lock();
        try {
            return cache.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    private void evictLeastRecentlyUsed() {
        while (!lruQueue.isEmpty()) {
            K lruKey = lruQueue.removeLast();
            CacheEntry<V> entry = cache.get(lruKey);
            if (entry != null && !isExpired(entry)) {
                cache.remove(lruKey);
                break;
            } else {
                cache.remove(lruKey);
            }
        }
    }

    @Override
    public String toString() {
        lock.lock();
        try {
            StringJoiner joiner = new StringJoiner(", ", "{", "}");
            for (K key : lruQueue) {
                CacheEntry<V> entry = cache.get(key);
                if (entry != null && !isExpired(entry)) {
                    joiner.add(formatEntry(key, entry));
                }
            }
            return joiner.toString();
        } finally {
            lock.unlock();
        }
    }


    private String formatEntry(K key, CacheEntry<V> entry) {
        return key + "=" + entry.value;
    }


    private boolean isExpired(CacheEntry<V> entry) {
        return (System.currentTimeMillis() - entry.timestamp) > ttlMillis;
    }

    private static class CacheEntry<V> {
        final V value;
        final long timestamp;

        CacheEntry(V value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
    }
}