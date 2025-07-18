package com.example.lab2.repository;

import com.example.lab2.entity.Order;
import com.example.lab2.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail,Integer>
{
    List<OrderDetail> findByOrder(Order order);
}
