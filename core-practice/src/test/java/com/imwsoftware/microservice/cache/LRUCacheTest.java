package com.imwsoftware.microservice.cache;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    /**
     * Tests the removeEldestEntry method in the LRUCache class.
     * The method should ensure that the eldest entry is removed
     * when the cache size exceeds the specified capacity.
     */

    @Test
    void testRemoveEldestEntry_RemovesOldestWhenCapacityExceeded() {
        // Initialize an LRUCache with a capacity of 3
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // Add items to the cache
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        // Verify the cache contains all items
        assertEquals(3, cache.size());
        assertTrue(cache.containsKey(1));
        assertTrue(cache.containsKey(2));
        assertTrue(cache.containsKey(3));

        // Add another item, exceeding the capacity
        cache.put(4, "D");

        // Verify the eldest item (1, "A") is removed
        assertEquals(3, cache.size());
        assertFalse(cache.containsKey(1));
        assertTrue(cache.containsKey(2));
        assertTrue(cache.containsKey(3));
        assertTrue(cache.containsKey(4));
    }

    @Test
    void testRemoveEldestEntry_WhenCapacityNotExceeded() {
        // Initialize an LRUCache with a capacity of 3
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // Add items within the capacity
        cache.put(1, "A");
        cache.put(2, "B");

        // Verify the cache contains the expected items
        assertEquals(2, cache.size());
        assertTrue(cache.containsKey(1));
        assertTrue(cache.containsKey(2));

        // Ensure no items are removed when capacity is not exceeded
        assertFalse(cache.removeEldestEntry(Map.entry(1, "A"))); // This check is internal, capacity not exceeded
    }

    @Test
    void testRemoveEldestEntry_LRUOrderMaintained() {
        // Initialize an LRUCache with a capacity of 3
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // Add initial items
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        // Access the second item to make it recently used
        cache.get(2);

        // Add another item, exceeding the capacity
        cache.put(4, "D");

        // Verify the eldest unused item (1, "A") is removed, not the recently accessed one
        assertEquals(3, cache.size());
        assertFalse(cache.containsKey(1));
        assertTrue(cache.containsKey(2));
        assertTrue(cache.containsKey(3));
        assertTrue(cache.containsKey(4));
    }

    @Test
    void testRemoveEldestEntry_WhenEmptyCache() {
        // Initialize an empty LRUCache with a capacity of 3
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // Ensure no eldest entry is removed from an empty cache
        assertEquals(0, cache.size());
    }

    @Test
    void testEvictionOfLeastRecentlyUsedItem() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        cache.get(1); // now 2 is the least recently used
        cache.put(4, "four"); // 2 should be evicted

        assertEquals(3, cache.size());
        assertTrue(cache.containsKey(1));
        assertFalse(cache.containsKey(2));
        assertTrue(cache.containsKey(3));
        assertTrue(cache.containsKey(4));
        System.out.println(cache);
    }
}