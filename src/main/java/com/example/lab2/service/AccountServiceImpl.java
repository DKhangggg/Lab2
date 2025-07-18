package com.example.lab2.service;

import com.example.lab2.entity.Account;
import com.example.lab2.entity.Role;
import com.example.lab2.repository.AccountRepo;
import com.example.lab2.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Account authenticate(String accountName, String password) {
        return accountRepo.findAccountByAccountNameAndPassword(accountName, password);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Integer id) {
        return accountRepo.findById(id);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepo.findByAccountName(username);    }

    @Override
    public Account createAccount(String username, String password, String email) {
        Account account = new Account();
        account.setAccountName(username);
        account.setPassword(password);
        account.setEmail(email);

        Role customerRole = roleRepo.findAll().stream()
                .filter(r -> "ROLE_CUSTOMER".equals(r.getRoleName()))
                .findFirst()
                .orElse(null);
        account.setRole(customerRole);

        return accountRepo.save(account);    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepo.deleteById(id);
    }

    @Override
    public boolean accountExists(String username) {
        return accountRepo.findByAccountName(username) != null;
    }

    @Override
    public boolean isAdmin(Account account) {
        return account != null && "ROLE_ADMIN".equals(account.getRole().getRoleName());    }

    @Override
    public long countAccounts() {
        return accountRepo.count();
    }
}
