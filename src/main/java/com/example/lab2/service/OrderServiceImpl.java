package com.example.lab2.service;

import com.example.lab2.entity.Account;
import com.example.lab2.entity.Orchid;
import com.example.lab2.entity.Order;
import com.example.lab2.entity.OrderDetail;
import com.example.lab2.repository.OrderDetailRepo;
import com.example.lab2.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    public List<Order> findAll(){
        return orderRepo.findAll();
    }

    @Override
    public List<Order> getAllOrders()
        {
            return orderRepo.findAll();
        }


    @Override
    public List<Order> getOrdersByAccount(Account account) {
        return orderRepo.findByAccount(account);
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return orderRepo.findById(id);
    }

    @Override
    public Order createOrder(Account account, Orchid orchid, Integer quantity) {
        Order order = new Order();
        order.setAccount(account);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("pending");
        order.setTotalAmount(orchid.getPrice() * quantity);

        Order savedOrder = orderRepo.save(order);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(savedOrder);
        orderDetail.setOrchid(orchid);
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(orchid.getPrice());

        orderDetailRepo.save(orderDetail);

        return savedOrder;    }

    @Override
    public List<OrderDetail> getOrderDetails(Order order) {
        return orderDetailRepo.findByOrder(order);
    }

    @Override
    public boolean canUserViewOrder(Account user, Order order) {
        boolean isAdmin = "ROLE_ADMIN".equals(user.getRole().getRoleName());
        return isAdmin || order.getAccount().getAccountId()==user.getAccountId();
    }
    @Override
    public void saveOrder(Order order) {
        orderRepo.save(order);
    }
    @Override
    public List<Order> findByOrderStatus(String status) {
        return orderRepo.findByOrderStatus(status);
    }
    @Override
    public List<Order> findByAccountAndOrderStatus(Account loggedInUser, String status) {
        return orderRepo.findByAccountAndOrderStatus(loggedInUser, status);
    }
}
