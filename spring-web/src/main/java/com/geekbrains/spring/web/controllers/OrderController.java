package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.converters.OrderConverter;
import com.geekbrains.spring.web.dto.*;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.services.OrderService;
import com.geekbrains.spring.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderConverter orderConverter;

    @PostMapping
    public void createOrder(Principal principal, @RequestBody OrderDetailsDto orderDetailsDto) {
        User user = userService.findByUsername(principal.getName()).get();
        System.out.println("+++++++++++++++++++++++ " + user.getUsername() + " create order +++++++++++++++++++++++");
        orderService.save(user, orderDetailsDto);

        System.out.println();
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.findAll().stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/user")
    public List<OrderDto> getAllUserOrders(Principal principal) {

         return orderService.findAllOrdersUserId(principal.getName()).stream().map(orderConverter::entityToDto).collect(Collectors.toList());

    }
}
