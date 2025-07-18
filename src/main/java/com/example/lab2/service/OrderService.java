package com.example.lab2.service;

import com.example.lab2.entity.*;
import com.example.lab2.repository.OrderDetailRepo;
import com.example.lab2.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderService {

    public List<Order> getAllOrders();

    public List<Order> getOrdersByAccount(Account account);

    public Optional<Order> getOrderById(Integer id);

    public Order createOrder(Account account, Orchid orchid, Integer quantity);

    public List<OrderDetail> getOrderDetails(Order order);

    public boolean canUserViewOrder(Account user, Order order);

    void saveOrder(Order order);

    List<Order> findByOrderStatus(String status);

    List<Order> findByAccountAndOrderStatus(Account loggedInUser, String status);
}
