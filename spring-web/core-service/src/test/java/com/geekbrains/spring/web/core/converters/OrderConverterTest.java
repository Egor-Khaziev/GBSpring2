package com.geekbrains.spring.web.core.converters;

import com.geekbrains.spring.web.core.dto.OrderDto;
import com.geekbrains.spring.web.core.dto.OrderItemDto;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderItem;
import com.geekbrains.spring.web.core.entities.Product;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class OrderConverterTest {
    @Autowired
    private OrderConverter orderConverter;
//    @Spy
//    private Order order;
//    @Spy
//    private OrderItemDto orderItemDto;


    // TODO: unsupported
//    @Test
//    public void dtoToEntity() {
//        List<OrderItemDto> items = new ArrayList<>();
//        OrderItemDto orderItemDto = new OrderItemDto();
//        orderItemDto.setProductId(0L);
//        orderItemDto.setPrice(300);
//        orderItemDto.setProductTitle("product");
//        orderItemDto.setQuantity(3);
//        orderItemDto.setPricePerProduct(100);
//        items.add(orderItemDto);
//
//        OrderDto orderDto = new OrderDto();
//        orderDto.setId(0L);
//        orderDto.setAddress("address");
//        orderDto.setPhone("123456789");
//        orderDto.setUsername("test_user");
//        orderDto.setTotalPrice(300);
//        orderDto.setItems(items);
//
//
//        orderConverter.dtoToEntity(orderDto);
//    }
    @Test
    public void entityToDto() {
        List<OrderItem> items = new ArrayList<>();
        Order order = new Order();

        OrderItem orderItem = new OrderItem();
        orderItem.setId(5L);
        orderItem.setPrice(300);
        orderItem.setProduct(new Product(0L, "product", 100));
        orderItem.setQuantity(3);
        orderItem.setPricePerProduct(100);
        orderItem.setOrder(order);
        items.add(orderItem);

        order.setAddress("address");
        order.setPhone("123456789");
        order.setUsername("test_user");
        order.setTotalPrice(300);
        order.setItems(items);
        order.setId(10L);

        orderConverter.entityToDto(order);

        Assertions.assertEquals(10L,orderConverter.entityToDto(order).getId());
        Assertions.assertEquals(1,orderConverter.entityToDto(order).getItems().size());
        Assertions.assertEquals("address",orderConverter.entityToDto(order).getAddress());
        Assertions.assertEquals("test_user",orderConverter.entityToDto(order).getUsername());
        Assertions.assertEquals(300,orderConverter.entityToDto(order).getTotalPrice());
        Assertions.assertEquals(100,orderConverter.entityToDto(order).getItems().get(0).getPricePerProduct());
        Assertions.assertEquals(3,orderConverter.entityToDto(order).getItems().get(0).getQuantity());
        Assertions.assertEquals("123456789",orderConverter.entityToDto(order).getPhone());
        Assertions.assertEquals(0L,orderConverter.entityToDto(order).getItems().get(0).getProductId());
        Assertions.assertEquals("product",orderConverter.entityToDto(order).getItems().get(0).getProductTitle());

    }
}
