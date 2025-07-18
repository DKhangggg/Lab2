package com.example.lab2.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer categoryId;

    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Orchid> orchidList;

    public Category() {
    }
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public List<Orchid> getOrchidList() {
        return orchidList;
    }
    public void setOrchidList(List<Orchid> orchidList) {
        this.orchidList = orchidList;
    }
}
