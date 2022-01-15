package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.OrderDetailsDto;
import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.dto.*;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public void save(User user, OrderDetailsDto orderDetailsDto) {
        Cart cart = cartService.getCurrentCart();
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setTotalPrice(cart.getTotalPrice());
        order.setItems(new ArrayList<>());
        order.setUser(user);
        for (OrderItemDto o : cart.getItems()) {
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
