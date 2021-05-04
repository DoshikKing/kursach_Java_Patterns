package com.example.demo.repos;

import com.example.demo.components.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepo extends JpaRepository<Record, Long> {
    List<Record> findByOrderByDate();
    List<Record> findByOrderByTime();
    List<Record> findByOrderById();
    List<Record> findByOrderByShopId();
    Record findByDate(String cardNumber);
}
