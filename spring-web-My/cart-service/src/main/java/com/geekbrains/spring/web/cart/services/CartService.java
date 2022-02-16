package com.geekbrains.spring.web.cart.services;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;


import com.geekbrains.spring.web.api.stat.ProductStatDto;
import com.geekbrains.spring.web.cart.integrations.ProductsServiceIntegration;
import com.geekbrains.spring.web.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsServiceIntegration productsServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    public List<ProductStatDto> statisticList;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;



    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public String addToCart(String cartKey, Long productId) {
        try {
            ProductDto productDto = productsServiceIntegration.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
            execute(cartKey, c -> {
                c.add(productDto);
            });


            ProductStatDto productStatDto = new ProductStatDto(productDto.getId(), productDto.getTitle(), 1);
            if (statisticList == null) {
                statisticList = new ArrayList<>();
            }
            if (!statisticList.contains(productStatDto)) {
                statisticList.add(productStatDto);
            } else {
                statisticList.get(statisticList.indexOf(productStatDto)).setQuantity(
                        statisticList.get(statisticList.indexOf(productStatDto)).getQuantity() + 1);
            }
        } catch (ResourceNotFoundException RNFE){
            return RNFE.getMessage();
        }
        return "OK";
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}