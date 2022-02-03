package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.core.dto.Cart;
import com.geekbrains.spring.web.core.dto.OrderDetailsDto;
import com.geekbrains.spring.web.core.dto.OrderItemDto;
import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.repositories.OrdersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class OrderServiceTest {
//
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private  OrdersRepository ordersRepository;
//
//    @MockBean
//    private  CartService cartService;
//
//    @MockBean
//    private ProductsService productsService;
//
//

//
//    @Test
//    public void createOrder() {
//        String username = "testuser";
//        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
//        orderDetailsDto.setAddress("adress");
//        orderDetailsDto.setPhone("777");
//        Product product1 = new Product();
//        Product product2 = new Product();
//        String cartKey = "test_cartKey";
//        Cart currentCart = new Cart();
//        OrderItemDto orderItemDto1 = new OrderItemDto();
//        OrderItemDto orderItemDto2 = new OrderItemDto();
//        orderItemDto1.setProductTitle("testItemDto1");
//        orderItemDto1.setPricePerProduct(100);
//        orderItemDto1.setPrice(200);
//        orderItemDto1.setQuantity(2);
//        orderItemDto1.setProductId(0L);
//        orderItemDto2.setProductTitle("testItemDto2");
//        orderItemDto2.setPricePerProduct(50);
//        orderItemDto2.setPrice(50);
//        orderItemDto2.setQuantity(1);
//        orderItemDto2.setProductId(3L);
//        product1.setPrice(200);
//        product1.setId(0L);
//        product1.setTitle("product1");
//        product2.setPrice(100);
//        product2.setId(3L);
//        product2.setTitle("product2");
//        currentCart.getItems().add(orderItemDto1);
//        currentCart.getItems().add(orderItemDto2);
//
//        Mockito.doReturn(cartKey).when(cartService).getCartUuidFromSuffix(username);
//        Mockito.doReturn(currentCart).when(cartService).getCurrentCart(cartKey);
//        Mockito.doReturn(Optional.of(product1)).when(productsService).findById(0L);
//        Mockito.doReturn(Optional.of(product2)).when(productsService).findById(3L);
//
//        orderService.createOrder(username, orderDetailsDto);
//
//
//        Assertions.assertEquals(2,ordersRepository.findAllByUsername(username).get(0).getItems().size());
//
//
//    }




}
