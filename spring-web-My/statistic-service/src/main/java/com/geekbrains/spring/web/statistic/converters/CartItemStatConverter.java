package com.geekbrains.spring.web.statistic.converters;

import com.geekbrains.spring.web.api.carts.CartItemDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.stat.ProductStatDto;
import com.geekbrains.spring.web.statistic.entities.CartItemStat;
import org.springframework.stereotype.Component;

@Component
public class CartItemStatConverter {

    public CartItemStat DtoToEntity(ProductStatDto productStatDto){

        CartItemStat cartItemStat = new CartItemStat(
                productStatDto.getId(),
                productStatDto.getTitle(),
                productStatDto.getQuantity()
        );

        return cartItemStat;
    }

}
