package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.OrderItemDto;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    final private OrderItemService orderItemService;
    final private OrderRepository orderRepository;
    final private ProductService productService;
    final private UserService userService;
    final private CartService cartService;

    @Transactional
    public void save(Principal principal) {
        Order order = new Order();
        order.setPrice(cartService.getCurrentCart().getTotalPrice());
        order.setItems(new ArrayList<>());
        order.setUser(userService.findByUsername(principal.getName()).get());
        for (OrderItemDto o : cartService.getCurrentCart().getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(o.getQuantity());
            Product product = productService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(o.getQuantity())));
            orderItem.setPricePerProduct(product.getPrice());
            orderItem.setProduct(product);
            order.getItems().add(orderItem);
        }
        orderRepository.save(order);
        cartService.getCurrentCart().clear();
    }


    public List<Order> findAllOrdersUserId(String name) {
        return orderRepository.findOrdersByUser(userService.findByUsername(name).get());
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
