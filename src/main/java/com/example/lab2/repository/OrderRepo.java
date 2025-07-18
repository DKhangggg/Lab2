package com.example.lab2.repository;

import com.example.lab2.entity.Account;
import com.example.lab2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    List<Order> findByAccount(Account account);
    List<Order> findByOrderStatus(String orderStatus);
    List<Order> findByAccountAndOrderStatus(Account account, String orderStatus);
}
