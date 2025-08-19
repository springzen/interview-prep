package com.imwsoftware.microservice.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TTLThreadSafeLRUCacheTest {

    @Test
    void testEvictionOfLeastRecentlyUsedItem() {
        TTLThreadSafeLRUCache<Integer, String> cache = new TTLThreadSafeLRUCache<>(3, 10_000); // 10s TTL

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        cache.get(1); // now 2 is the least recently used
        cache.put(4, "four"); // 2 should be evicted

        assertEquals(3, cache.size());
        assertTrue(cache.containsKey(1), "Key 1 should still be present");
        assertFalse(cache.containsKey(2), "Key 2 should have been evicted");
        assertTrue(cache.containsKey(3), "Key 3 should still be present");
        assertTrue(cache.containsKey(4), "Key 4 should have been added");
        System.out.println(cache);
    }

    @Test
    void testEntryExpiresAfterTTL() throws InterruptedException {
        TTLThreadSafeLRUCache<Integer, String> cache = new TTLThreadSafeLRUCache<>(3, 100); // TTL = 100 ms

        cache.put(1, "one");
        Thread.sleep(150); // Wait for TTL to expire

        String value = cache.get(1);

        assertNull(value, "Expected key 1 to expire and return null");
        assertFalse(cache.containsKey(1), "Key 1 should no longer be present");
        System.out.println(cache);
    }
}