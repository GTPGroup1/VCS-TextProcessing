package com.group1.vcstextprocessing.model;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<DataItem> dataList = new ArrayList<>();

    public void addData(DataItem dataItem) {
        dataList.add(dataItem);
    }

    public void updateData(int index, DataItem newDataItem) {
        if (index >= 0 && index < dataList.size()) {
            dataList.set(index, newDataItem);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void deleteData(int index) {
        if (index >= 0 && index < dataList.size()) {
            dataList.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public void setDataList(List<DataItem> dataList) {
        this.dataList = dataList;
    }

    public List<DataItem> getAllData() {
        return new ArrayList<>(dataList); // Return a copy to prevent external modification
    }

}
