package com.example.demo.service.emailService;

import com.example.demo.components.Shop;
import com.example.demo.components.Record;

public interface emailServiceInterface {

    void sendMessageAboutShop(Shop shop);

    void sendMessageAboutRecord(Record record);
}
