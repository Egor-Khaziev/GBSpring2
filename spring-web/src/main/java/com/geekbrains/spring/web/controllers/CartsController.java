package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartsController {
    private final CartService cartService;

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProductByIdToCart(id);
    }

    @GetMapping("/reduce/{id}")
    public void deleteProductToCart(@PathVariable Long id) {
        cartService.reduceProductByIdToCart(id);
    }

    @GetMapping("/clear/{id}")
    public void clearProductToCart(@PathVariable Long id) {
        cartService.clearProductByIdToCart(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.getCurrentCart().clear();
    }
}
