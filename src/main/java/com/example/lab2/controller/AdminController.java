package com.example.lab2.controller;

import com.example.lab2.entity.Account;
import com.example.lab2.entity.Category;
import com.example.lab2.entity.Orchid;
import com.example.lab2.service.AccountService;
import com.example.lab2.service.CategoryService;
import com.example.lab2.service.OrchidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrchidService orchidService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccountService accountService;

    // Check admin access
    private boolean isAdmin(HttpSession session) {
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        return loggedInUser != null && "ROLE_ADMIN".equals(loggedInUser.getRole().getRoleName());
    }

    // Admin Dashboard
    @GetMapping({"", "/"})
    public String adminDashboard(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/orchids";
        }

        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        List<Orchid> orchids = orchidService.findAll();
        List<Category> categories = categoryService.findAll();
        List<Account> accounts = accountService.getAllAccounts();

        model.addAttribute("orchids", orchids);
        model.addAttribute("categories", categories);
        model.addAttribute("accounts", accounts);
        model.addAttribute("loggedInUser", loggedInUser);

        return "admin";
    }

    // Add Orchid
    @PostMapping("/orchids/add")
    public String addOrchid(@RequestParam String name,
                           @RequestParam String description,
                           @RequestParam Double price,
                           @RequestParam Integer categoryId,
                           @RequestParam(required = false) String url,
                           @RequestParam(required = false) Boolean is_natural,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/orchids";
        }

        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                redirectAttributes.addFlashAttribute("error", "Category not found");
                return "redirect:/admin";
            }

            Orchid orchid = new Orchid();
            orchid.setName(name);
            orchid.setDescription(description);
            orchid.setPrice(price);
            orchid.setCategory(category);
            orchid.setUrl(url);
            orchid.setIs_natural(is_natural != null ? is_natural : false);

            orchidService.saveOrchid(orchid);
            redirectAttributes.addFlashAttribute("success", "Orchid added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding orchid: " + e.getMessage());
        }

        return "redirect:/admin";
    }

    // Delete Orchid
    @PostMapping("/orchids/delete/{id}")
    public String deleteOrchid(@PathVariable Integer id,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/orchids";
        }

        try {
            orchidService.deleteOrchid(id);
            redirectAttributes.addFlashAttribute("success", "Orchid deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting orchid: " + e.getMessage());
        }

        return "redirect:/admin";
    }

    // Add Category
    @PostMapping("/categories/add")
    public String addCategory(@RequestParam String categoryName,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/orchids";
        }

        try {
            Category category = new Category();
            category.setCategoryName(categoryName);
            categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("success", "Category added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding category: " + e.getMessage());
        }

        return "redirect:/admin";
    }

    // Delete Category
    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/orchids";
        }

        try {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("success", "Category deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting category: " + e.getMessage());
        }

        return "redirect:/admin";
    }

    // Delete Account
    @PostMapping("/accounts/delete/{id}")
    public String deleteAccount(@PathVariable Integer id,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/orchids";
        }

        try {
            Account currentAdmin = (Account) session.getAttribute("loggedInUser");
            if (currentAdmin.getAccountId() == id) {
                redirectAttributes.addFlashAttribute("error", "Cannot delete your own account!");
                return "redirect:/admin";
            }

            accountService.deleteAccount(id);
            redirectAttributes.addFlashAttribute("success", "Account deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting account: " + e.getMessage());
        }

        return "redirect:/admin";
    }
}
