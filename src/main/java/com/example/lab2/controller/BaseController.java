package com.example.lab2.controller;

import com.example.lab2.entity.Account;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

public abstract class BaseController {

    protected boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInUser") != null;
    }
    protected Account getLoggedInUser(HttpSession session) {
        return (Account) session.getAttribute("loggedInUser");
    }
    protected boolean isAdmin(Account user) {
        return user != null && user.getRole() != null && 
               "ROLE_ADMIN".equals(user.getRole().getRoleName());
    }
    protected boolean isCustomer(Account user) {
        return user != null && user.getRole() != null && 
               "ROLE_CUSTOMER".equals(user.getRole().getRoleName());
    }
    protected boolean isStaff(Account user) {
        return user != null && user.getRole() != null && 
               "ROLE_STAFF".equals(user.getRole().getRoleName());
    }
    protected String redirectToLoginIfNotAuthenticated(HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/auth/login";
        }
        return null;
    }
    protected void addCommonAttributes(Model model, HttpSession session, String title) {
        Account loggedInUser = getLoggedInUser(session);
        model.addAttribute("loggedInUser", loggedInUser);
        if (title != null) {
            model.addAttribute("title", title);
        }
    }
    protected void validateRequired(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        if (value instanceof String && ((String) value).trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }
    protected void validateId(Integer id, String entityName) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid " + entityName + " ID");
        }
    }
}
