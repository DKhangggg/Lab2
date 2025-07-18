package com.example.lab2.controller;

import com.example.lab2.entity.*;
import com.example.lab2.service.OrderService;
import com.example.lab2.service.OrchidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrchidService orchidService;

    @GetMapping({"", "/"})
    public String orderPage(Model model, HttpSession session) {
        String redirectResult = redirectToLoginIfNotAuthenticated(session);
        if (redirectResult != null) return redirectResult;

        List<Orchid> orchids = orchidService.findAll();
        model.addAttribute("orchids", orchids);
        addCommonAttributes(model, session, "Đặt hàng");

        return "order-orchids";
    }

    // Add to Order
    @PostMapping("/add")
    public String addToOrder(@RequestParam Integer orchidId,
                            @RequestParam Integer quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        String redirectResult = redirectToLoginIfNotAuthenticated(session);
        if (redirectResult != null) return redirectResult;

        try {
            validateId(orchidId, "Orchid");
            validateRequired(quantity, "Quantity");

            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }

            Account user = getLoggedInUser(session);
            Optional<Orchid> orchidOpt = orchidService.getOrchidById(orchidId);

            if (orchidOpt.isEmpty()) {
                throw new IllegalArgumentException("Orchid not found");
            }

            Orchid orchid = orchidOpt.get();
            Order savedOrder = orderService.createOrder(user, orchid, quantity);

            redirectAttributes.addFlashAttribute("success",
                "Order created successfully! Order #" + savedOrder.getOrderId());

            return "redirect:/orders/" + savedOrder.getOrderId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating order: " + e.getMessage());
            return "redirect:/orchids";
        }
    }

    // Xem đơn hàng của user
    @GetMapping("/my-orders")
    public String myOrders(Model model, HttpSession session) {
        String redirectResult = redirectToLoginIfNotAuthenticated(session);
        if (redirectResult != null) return redirectResult;

        Account user = getLoggedInUser(session);
        List<Order> orders = orderService.getOrdersByAccount(user);

        model.addAttribute("orders", orders);
        addCommonAttributes(model, session, "Đơn hàng của tôi");

        return "my-orders";
    }

    // Order detail page
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Integer id, Model model, HttpSession session) {
        String redirectResult = redirectToLoginIfNotAuthenticated(session);
        if (redirectResult != null) return redirectResult;

        try {
            Account loggedInUser = getLoggedInUser(session);
            Optional<Order> orderOpt = orderService.getOrderById(id);

            if (orderOpt.isEmpty()) {
                return "redirect:/orchids";
            }

            Order order = orderOpt.get();

            // Check if user has permission to view this order
            if (!orderService.canUserViewOrder(loggedInUser, order)) {
                return "redirect:/orchids";
            }

            List<OrderDetail> orderDetails = orderService.getOrderDetails(order);
            
            model.addAttribute("order", order);
            model.addAttribute("orderDetails", orderDetails);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("title", "Order #" + order.getOrderId());
            
            return "order-detail";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading order details. Please try again.");
            return "redirect:/orders";
        }
    }

    // Update order status (Admin only)
    @PostMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable Integer id, 
                                   @RequestParam String status,
                                   HttpSession session,
                                   Model model) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/auth/login";
        }
        
        try {
            Account loggedInUser = getLoggedInUser(session);
            
            if (!isAdmin(loggedInUser)) {
                model.addAttribute("error", "You don't have permission to update order status");
                return "redirect:/orders";
            }
            
            Optional<Order> orderOpt = orderService.getOrderById(id);
            if (orderOpt.isEmpty()) {
                model.addAttribute("error", "Order not found");
                return "redirect:/orders";
            }
            
            Order order = orderOpt.get();
            order.setOrderStatus(status);
            orderService.saveOrder(order);
            
            model.addAttribute("success", "Order status updated successfully");
            return "redirect:/orders/" + id;
        } catch (Exception e) {
            model.addAttribute("error", "Error updating order status. Please try again.");
            return "redirect:/orders/" + id;
        }
    }

    // Get orders by status (for filtering)
    @GetMapping("/status/{status}")
    public String ordersByStatus(@PathVariable String status, Model model, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/auth/login";
        }
        
        try {
            Account loggedInUser = getLoggedInUser(session);
            List<Order> orders;
            
            if (isAdmin(loggedInUser)) {
                orders = orderService.findByOrderStatus(status);
            } else {
                orders = orderService.findByAccountAndOrderStatus(loggedInUser, status);
            }
            
            model.addAttribute("orders", orders);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("selectedStatus", status);
            model.addAttribute("title", "Orders - " + status.toUpperCase());
            
            return "orders";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading orders. Please try again.");
            return "redirect:/orders";
        }
    }
}
