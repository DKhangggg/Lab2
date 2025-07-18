package com.example.lab2.service;

import com.example.lab2.entity.Category;
import com.example.lab2.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }
    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepo.findById(id).orElse(null);
    }
    @Override
    public void saveCategory(Category category) {
        categoryRepo.save(category);
    }
    @Override
    public void deleteCategory(Integer id) {
        categoryRepo.deleteById(id);
    }
}
