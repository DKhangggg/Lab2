package com.example.lab2.repository;

import com.example.lab2.entity.Orchid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrchidRepo extends JpaRepository<Orchid,Integer> {
    Optional<Orchid> getOrchidByOrchidId(Integer orchidId);
    @Query("SELECT o FROM Orchid o WHERE o.category.categoryId = ?1")
    List<Orchid> findAllByCategoryId(Integer categoryId);
}
