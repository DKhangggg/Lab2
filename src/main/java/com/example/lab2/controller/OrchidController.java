package com.example.lab2.controller;

import com.example.lab2.entity.Account;
import com.example.lab2.entity.Category;
import com.example.lab2.entity.Orchid;
import com.example.lab2.service.CategoryService;
import com.example.lab2.service.OrchidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orchids")
public class OrchidController {

    @Autowired
    private OrchidService orchidService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"", "/"})
    public String orchidsPage(@RequestParam(required = false) Integer categoryId,
                             @RequestParam(required = false) String search,
                             Model model, HttpSession session) {
        
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        List<Orchid> orchids;
        List<Category> categories = categoryService.findAll();

        if (categoryId != null) {
            orchids = orchidService.getOrchidsByCategoryId(categoryId);
        } else if (search != null && !search.trim().isEmpty()) {
            orchids = orchidService.searchOrchids(search);
        } else {
            orchids = orchidService.findAll();
        }
        
        model.addAttribute("orchids", orchids);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("searchQuery", search);
        model.addAttribute("loggedInUser", loggedInUser);
        
        return "orchids";
    }
}
