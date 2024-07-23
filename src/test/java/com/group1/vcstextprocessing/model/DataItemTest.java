package com.group1.vcstextprocessing.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DataItemTest {

    @Test
    void testConstructorAndGetters() {
        DataItem item = new DataItem(1, "test data");
        assertEquals(1, item.getId(), "The ID should match the one provided to the constructor.");
        assertEquals("test data", item.getData(), "The data should match the one provided to the constructor.");
    }

    @Test
    void testEqualsWithSameObject() {
        DataItem item = new DataItem(1, "test data");
        assertTrue(item.equals(item), "An object should be equal to itself.");
    }

    @Test
    void testEqualsWithDifferentObject() {
        DataItem item1 = new DataItem(1, "test data");
        DataItem item2 = new DataItem(1, "test data");
        DataItem item3 = new DataItem(2, "test data");
        DataItem item4 = new DataItem(1, "different data");

        assertTrue(item1.equals(item2), "Two objects with the same id and data should be equal.");
        assertFalse(item1.equals(item3), "Two objects with different ids should not be equal.");
        assertFalse(item1.equals(item4), "Two objects with different data should not be equal.");
    }

    @Test
    void testEqualsWithNull() {
        DataItem item = new DataItem(1, "test data");
        assertFalse(item.equals(null), "An object should not be equal to null.");
    }

    @Test
    void testEqualsWithDifferentClass() {
        DataItem item = new DataItem(1, "test data");
        Object other = new Object();
        assertFalse(item.equals(other), "A DataItem should not be equal to an object of a different class.");
    }

    @Test
    void testHashCode() {
        DataItem item1 = new DataItem(1, "test data");
        DataItem item2 = new DataItem(1, "test data");
        assertEquals(item1.hashCode(), item2.hashCode(), "Hash codes of equal objects should be the same.");
    }

    @Test
    void testToString() {
        DataItem item = new DataItem(1, "test data");
        assertEquals("test data", item.toString(), "The toString method should return the correct data.");
    }
}
