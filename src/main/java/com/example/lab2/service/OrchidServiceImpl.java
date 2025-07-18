package com.example.lab2.service;

import com.example.lab2.entity.Orchid;
import com.example.lab2.repository.OrchidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrchidServiceImpl implements OrchidService {

    @Autowired
    private OrchidRepo orchidRepo;

    @Override
    public List<Orchid> findAll() {
        return orchidRepo.findAll();
    }

    @Override
    public Optional<Orchid> getOrchidById( Integer orchidId) {
        return orchidRepo.getOrchidByOrchidId(orchidId);
    }
    @Override
    public void saveOrchid(Orchid orchid) {
        orchidRepo.save(orchid);
    }

    @Override
    public void deleteOrchid(Integer id) {
        orchidRepo.deleteById(id);
    }
    @Override
    public List<Orchid> getOrchidsByCategoryId(Integer categoryId) {
        return orchidRepo.findAllByCategoryId(categoryId);
    }
    @Override
    public List<Orchid> searchOrchids(String search) {
        return orchidRepo.findAll().stream()
                .filter(orchid -> orchid.getName().toLowerCase().contains(search.toLowerCase()))
                .toList();
    }
}
