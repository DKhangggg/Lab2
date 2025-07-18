package com.example.lab2.service;

import com.example.lab2.entity.Account;
import com.example.lab2.entity.Role;
import com.example.lab2.repository.AccountRepo;
import com.example.lab2.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface AccountService {
    public Account authenticate(String accountName, String password);
    public List<Account> getAllAccounts();
    public Optional<Account> getAccountById(Integer id);
    public Account getAccountByUsername(String username);
    public Account createAccount(String username, String password, String email) ;
    public void deleteAccount(Integer id);
    public boolean accountExists(String username);
    public boolean isAdmin(Account account);
    public long countAccounts();
}
