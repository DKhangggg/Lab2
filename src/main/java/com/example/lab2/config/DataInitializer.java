package com.example.lab2.config;

import com.example.lab2.entity.*;
import com.example.lab2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private OrchidRepo orchidRepo;
    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Override
    public void run(String... args) throws Exception {

        List<Role> roles = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<Orchid> orchids = new ArrayList<>();
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (roleRepo.findAll().isEmpty()) {
            Role adminRole = new Role("ROLE_ADMIN");
            Role customerRole = new Role("ROLE_CUSTOMER");
            Role staffRole = new Role("ROLE_STAFF");

            roleRepo.save(adminRole);
            roleRepo.save(customerRole);
            roleRepo.save(staffRole);

            roles = List.of(adminRole, customerRole, staffRole);
        }
        else roles = roleRepo.findAll();

        roles.forEach(role -> System.out.println(role.toString()));

        if(accountRepo.findAll().isEmpty()) {
             accounts = List.of(
                    new Account("admin", "admin@example.com", "1",roles.get(0)),
                    new Account( "user", "user@example.com", "1",roles.get(1)),
                    new Account( "staff", "staff@example.com", "1",roles.get(2))
             );
            accounts.forEach(accountRepo::save);
        } else  accounts = accountRepo.findAll();

        accounts.forEach(account -> System.out.println(account.toString()));
        if( orderRepo.findAll().isEmpty()) {
            orders = List.of(
                    new Order(accounts.get(0), LocalDateTime.now(), "pending", 1000.0),
                    new Order(accounts.get(1), LocalDateTime.now(), "completed", 500.0),
                    new Order(accounts.get(2), LocalDateTime.now(), "transfer", 500.0)
            );
            orders.forEach(orderRepo::save);
        }
        else  orders = orderRepo.findAll();

        orders.forEach(order -> System.out.println(order.toString()));

        if (categoryRepo.findAll().isEmpty()) {
            categories = List.of(
                    new Category("Natural Orchid"),
                    new Category("Artificial Orchid")
            );
            categories.forEach(categoryRepo::save);
        } else {
            categories = categoryRepo.findAll();
        }
        categories.forEach(category -> System.out.println(category.toString()));

        if (orchidRepo.findAll().isEmpty()) {
            orchids = List.of(
                    new Orchid(true, "Beautiful natural orchid", "Orchid A", "/img/orchidA.jpg", 150.0, categories.get(0)),
                    new Orchid(false, "Silk artificial orchid", "Orchid B", "/img/orchidB.jpg", 90.0, categories.get(1))
            );
            orchids.forEach(orchidRepo::save);
        } else {
            orchids = orchidRepo.findAll();
        }

        orchids.forEach(orchid -> System.out.println(orchid.toString()));

        if (orderDetailRepo.findAll().isEmpty()) {
            orderDetails = List.of(
                    new OrderDetail(orchids.get(0), 150.0, 2, orders.get(0)),
                    new OrderDetail(orchids.get(1), 90.0, 1, orders.get(1)),
                    new OrderDetail(orchids.get(0), 150.0, 1, orders.get(2))
            );
            orderDetails.forEach(orderDetailRepo::save);
        } else {
            orderDetails = orderDetailRepo.findAll();
        }

    }
}
