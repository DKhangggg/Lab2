package com.example.lab2.service;

import com.example.lab2.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category getCategoryById(Integer categoryId);
    void saveCategory(Category category);
    void deleteCategory(Integer categoryId);
}
