package com.example.demo.service;

import com.example.demo.components.Record;
import com.example.demo.components.Shop;

import java.util.List;

public interface recordServiceInterface {
    void addRecord(Record record);
    List<Record> getAllRecords();
    List<Record> filterByDate();
    List<Record> filterByTime();
    List<Record> filterByRecordId();
    List<Record> filterByRelatedShopId();
    Record getRecordById(Long id);
    void deleteRecordById(Long id);
    void deleteAllRecords();
    Shop getShopByRecord(Long id);
    Long getRecordId(String date);
}
