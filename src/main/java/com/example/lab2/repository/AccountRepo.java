package com.example.lab2.repository;

import com.example.lab2.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    
    

    Account findAccountByAccountNameAndPassword(String accountName, String password);

    Account findByAccountName(String accountName);
}
