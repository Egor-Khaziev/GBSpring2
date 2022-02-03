package com.geekbrains.spring.web.core.dto;

import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.services.CartService;
import com.geekbrains.spring.web.core.services.ProductsService;
import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest //(classes = {CartService.class, ProductsService.class, Product.class, Cart.class})
public class CartTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductsService productsService;


    @BeforeEach
    public void initCart() {
        //TODO: cart.clear() do not work, need use cartService.clearCart (cartService.cartClear repaired)
        // cart.clear() need repair.
        cartService.clearCart("test_cart");


    }
//

    @Test
    public void addOneProduct() {
        Product product5 = new Product(5L, "product_test", 50);

        Mockito.doReturn(Optional.of(product5)).when(productsService).findById(5L);

        cartService.addToCart("test_cart", 5L);

        Mockito.verify(productsService, Mockito.times(1)).findById(ArgumentMatchers.eq(5L));
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(50, cartService.getCurrentCart("test_cart").getItems().get(0).getPrice());
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().get(0).getQuantity());
        Assertions.assertEquals(50, cartService.getCurrentCart("test_cart").getTotalPrice());
    }

    @Test
    public void addSameProducts() {
        Product product5 = new Product(5L, "product_test", 50);

        Mockito.doReturn(Optional.of(product5)).when(productsService).findById(5L);

        cartService.addToCart("test_cart", 5L);
        cartService.addToCart("test_cart", 5L);

        //TODO: 2 connections to base need repair CartService.add(...) // need 1 connection
        Mockito.verify(productsService, Mockito.times(2)).findById(ArgumentMatchers.eq(5L));
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(100, cartService.getCurrentCart("test_cart").getItems().get(0).getPrice());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().get(0).getQuantity());
        Assertions.assertEquals(100, cartService.getCurrentCart("test_cart").getTotalPrice());
    }

    @Test
    public void addDifferentProducts() {
        Product product5 = new Product(5L, "product_test", 50);
        Product product2 = new Product(2L, "product_test", 100);

        Mockito.doReturn(Optional.of(product5)).when(productsService).findById(5L);
        Mockito.doReturn(Optional.of(product2)).when(productsService).findById(2L);

        cartService.addToCart("test_cart", 5L);
        cartService.addToCart("test_cart", 2L);
        cartService.addToCart("test_cart", 5L);
        cartService.addToCart("test_cart", 2L);

        //TODO: 4 connections to base need repair CartService.add(...) // need 2 connections (1 connection for each)
        Mockito.verify(productsService, Mockito.times(2)).findById(ArgumentMatchers.eq(5L));
        Mockito.verify(productsService, Mockito.times(2)).findById(ArgumentMatchers.eq(2L));
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(100, cartService.getCurrentCart("test_cart").getItems().get(0).getPrice());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().get(0).getQuantity());
        Assertions.assertEquals(200, cartService.getCurrentCart("test_cart").getItems().get(1).getPrice());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().get(1).getQuantity());
        Assertions.assertEquals(300, cartService.getCurrentCart("test_cart").getTotalPrice());


    }

}
