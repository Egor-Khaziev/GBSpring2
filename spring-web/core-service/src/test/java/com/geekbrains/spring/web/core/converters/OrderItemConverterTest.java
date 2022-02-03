package com.geekbrains.spring.web.core.converters;

import com.geekbrains.spring.web.core.dto.OrderItemDto;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderItem;
import com.geekbrains.spring.web.core.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest(classes = {OrderItemDto.class, OrderItem.class, OrderItemConverter.class, Product.class, Order.class})
public class OrderItemConverterTest {
    @Autowired
    private OrderItemConverter orderItemConverter;

    //TODO: unsupported
//    @Test
//    public void dtoToEntity() {
//        OrderItemDto orderItemDto = new OrderItemDto();
//        orderItemDto.setProductId(0L);
//        orderItemDto.setQuantity(1);
//        orderItemDto.setPrice(50);
//        orderItemDto.setPricePerProduct(50);
//        orderItemDto.setProductTitle("product_test");
//
//        orderItemConverter.dtoToEntity(orderItemDto);
//    }

    @Test
    public void entityToDtoOneProduct() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(0L);
        orderItem.setPrice(50);
        orderItem.setProduct(new Product(0L, "product", 50));
        orderItem.setQuantity(1);
        orderItem.setPricePerProduct(50);
        orderItem.setOrder(new Order());

        OrderItemDto orderItemDto = orderItemConverter.entityToDto(orderItem);

        Assertions.assertEquals(1, orderItemDto.getQuantity());
        Assertions.assertEquals(0L, orderItemDto.getProductId());
        Assertions.assertEquals(50, orderItemDto.getPrice());
        Assertions.assertEquals("product", orderItemDto.getProductTitle());
        Assertions.assertEquals(50, orderItemDto.getPricePerProduct());
    }
        @Test
        public void entityToDto2Products() {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(0L);
            orderItem.setPrice(100);
            orderItem.setProduct(new Product(0L, "product", 50));
            orderItem.setQuantity(2);
            orderItem.setPricePerProduct(50);
            orderItem.setOrder(new Order());

            OrderItemDto orderItemDto = orderItemConverter.entityToDto(orderItem);

            Assertions.assertEquals(2, orderItemDto.getQuantity());
            Assertions.assertEquals(0L, orderItemDto.getProductId());
            Assertions.assertEquals(100, orderItemDto.getPrice());
            Assertions.assertEquals("product", orderItemDto.getProductTitle());
            Assertions.assertEquals(50, orderItemDto.getPricePerProduct());

    }
}
