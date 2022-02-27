package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.api.core.OrderDetailsDto;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderItem;
import com.geekbrains.spring.web.core.entities.OrderStatus;
import com.geekbrains.spring.web.core.integrations.CartServiceIntegration;
import com.geekbrains.spring.web.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductsService productsService;

    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setAddressLine1(orderDetailsDto.getAddressLine1());
        order.setAddressLine2(orderDetailsDto.getAddressLine2());
        order.setPostalCode(orderDetailsDto.getPostalCode());
        order.setCountryCode(orderDetailsDto.getCountryCode());
        order.setAdminArea1(orderDetailsDto.getAdminArea1());
        order.setAdminArea2(orderDetailsDto.getAdminArea2());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setStatus(String.valueOf(OrderStatus.CREATED));
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        ordersRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }

    @Transactional
    public void orderCancel(Long id) {
        Order order = findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404"));
        order.setStatus(String.valueOf(OrderStatus.CANCELED));
        ordersRepository.save(order);
    }

    @Transactional
    public void orderPaid(Long id) {
        Order order = findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404"));
        order.setStatus(String.valueOf(OrderStatus.PAID));
        ordersRepository.save(order);
    }
}
