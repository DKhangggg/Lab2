package com.example.lab2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orchids")
public class Orchid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orchidId;
    private boolean is_natural;
    private String description;
    private String name;
    private String url;
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    public Orchid() {
    }
    public Orchid(boolean is_natural, String description, String name, String url, double price, Category category) {
        this.is_natural = is_natural;
        this.description = description;
        this.name = name;
        this.url = url;
        this.price = price;
        this.category = category;
    }

    public int getOrchidId() {
        return orchidId;
    }

    public void setOrchidId(int orchidId) {
        this.orchidId = orchidId;
    }

    public boolean isIs_natural() {
        return is_natural;
    }

    public void setIs_natural(boolean is_natural) {
        this.is_natural = is_natural;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
