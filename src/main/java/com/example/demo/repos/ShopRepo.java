package com.example.demo.repos;

import com.example.demo.components.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepo extends JpaRepository<Shop, Long> {
    List<Shop> findByOrderByName();
    List<Shop> findByOrderByAddress();
    List<Shop> findByOrderById();
    Shop findByName(String name);
}
