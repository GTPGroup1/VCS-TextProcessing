package com.group1.vcstextprocessing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class DataManagerTest {

    private DataManager dataManager;

    @BeforeEach
    void setUp() {
        dataManager = new DataManager();
    }

    @Test
    void testAddData() {
        DataItem dataItem = new DataItem(1, "test data");
        dataManager.addData(dataItem);
        assertEquals(1, dataManager.getAllData().size(), "The data list should have one item after addition.");
        assertEquals(dataItem, dataManager.getAllData().get(0), "The added item should be the same as the one retrieved.");
    }

    @Test
    void testUpdateDataValidIndex() {
        DataItem originalItem = new DataItem(1, "original data");
        DataItem newItem = new DataItem(1, "updated data");

        dataManager.addData(originalItem);
        dataManager.updateData(0, newItem);

        assertEquals(newItem, dataManager.getAllData().get(0), "The data should be updated with the new item.");
    }

    @Test
    void testUpdateDataInvalidIndex() {
        DataItem newItem = new DataItem(1, "updated data");

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            dataManager.updateData(0, newItem);
        });

        assertEquals("Invalid index", exception.getMessage(), "Expected exception for invalid index.");
    }

    @Test
    void testDeleteDataValidIndex() {
        DataItem dataItem = new DataItem(1, "test data");
        dataManager.addData(dataItem);
        dataManager.deleteData(0);
        assertTrue(dataManager.getAllData().isEmpty(), "The data list should be empty after deletion.");
    }

    @Test
    void testDeleteDataInvalidIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            dataManager.deleteData(0);
        });

        assertEquals("Invalid index", exception.getMessage(), "Expected exception for invalid index.");
    }

    @Test
    void testSetDataList() {
        List<DataItem> newDataList = List.of(new DataItem(1, "data 1"), new DataItem(2, "data 2"));
        dataManager.setDataList(newDataList);
        assertEquals(newDataList, dataManager.getAllData(), "The data list should be replaced with the new list.");
    }

    @Test
    void testGetAllDataReturnsCopy() {
        DataItem dataItem = new DataItem(1, "test data");
        dataManager.addData(dataItem);
        List<DataItem> retrievedData = dataManager.getAllData();
        retrievedData.remove(0);

        assertFalse(dataManager.getAllData().isEmpty(), "Modifying the retrieved list should not affect the original list.");
    }
}