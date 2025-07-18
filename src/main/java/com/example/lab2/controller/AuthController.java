package com.example.lab2.controller;

import com.example.lab2.entity.Account;
import com.example.lab2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AccountService accountService;

    // Login page
    @GetMapping({"/", "/login"})
    public String loginPage(HttpSession session) {
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            if ("ROLE_ADMIN".equals(loggedInUser.getRole().getRoleName())) {
                return "redirect:/admin";
            }
            return "redirect:/orchids";
        }
        return "login";
    }

    // Register page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Handle login
    @PostMapping("/login")
    public String login(@RequestParam String accountName,
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {

        Account account = accountService.authenticate(accountName, password);

        if (account != null) {
            session.setAttribute("loggedInUser", account);
            if ("ROLE_ADMIN".equals(account.getRole().getRoleName())) {
                return "redirect:/admin";
            }
            return "redirect:/orchids";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }

    // Handle register
    @PostMapping("/register")
    public String register(@RequestParam String accountName,
                          @RequestParam String password,
                          @RequestParam String email,
                          RedirectAttributes redirectAttributes) {

        try {
            // Check if username exists
            if (accountService.accountExists(accountName)) {
                redirectAttributes.addFlashAttribute("error", "Username already exists");
                return "redirect:/register";
            }

            accountService.createAccount(accountName, password, email);

            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
