package com.example.lab2.service;

import com.example.lab2.entity.Account;
import com.example.lab2.entity.Orchid;
import com.example.lab2.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrchidService {
    public List<Orchid> findAll();
    Optional<Orchid> getOrchidById(Integer orchidId);
    void deleteOrchid(Integer id);
    void saveOrchid(Orchid orchid);
    List<Orchid> getOrchidsByCategoryId(Integer categoryId);
    List<Orchid> searchOrchids(String search);

}
