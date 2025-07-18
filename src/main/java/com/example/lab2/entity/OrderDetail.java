package com.example.lab2.entity;

import jakarta.persistence.*;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orchid_id")
    private Orchid orchid;

    private double price;
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderDetail() {
    }
    public OrderDetail(Orchid orchid, double price, int quantity, Order order) {
        this.orchid = orchid;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Orchid getOrchid() {
        return orchid;
    }

    public void setOrchid(Orchid orchid) {
        this.orchid = orchid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
