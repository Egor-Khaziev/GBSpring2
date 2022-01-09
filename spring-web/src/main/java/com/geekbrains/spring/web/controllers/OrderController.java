package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.dto.OrderDto;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.services.OrderService;
import com.geekbrains.spring.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public void createOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        System.out.println("+++++++++++++++++++++++ " + user.getUsername() + " create order +++++++++++++++++++++++");
        orderService.save(principal);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.findAll().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("/user")
    public List<OrderDto> getAllUserOrders(Principal principal) {

         return orderService.findAllOrdersUserId(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());

    }
}
