package com.example.demo.service;

import com.example.demo.components.Shop;

import java.util.List;

public interface shopServiceInterface {
    void addShop(Shop shop);
    List<Shop> getAllShops();
    List<Shop> filterByName();
    List<Shop> filterByAddress();
    List<Shop> filterByShopId();
    Shop getShopById(Long id);
    void deleteShopById(Long id);
    void deleteAllShops();
    Long getBankId(String name);
}
