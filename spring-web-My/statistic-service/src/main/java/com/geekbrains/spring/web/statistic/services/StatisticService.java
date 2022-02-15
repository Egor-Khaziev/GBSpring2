package com.geekbrains.spring.web.statistic.services;

import com.geekbrains.spring.web.api.carts.CartItemDto;
import com.geekbrains.spring.web.api.core.OrderItemDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.stat.ProductStatDto;
import com.geekbrains.spring.web.statistic.converters.CartItemStatConverter;
import com.geekbrains.spring.web.statistic.converters.OrderItemStatConverter;
import com.geekbrains.spring.web.statistic.entities.CartItemStat;
import com.geekbrains.spring.web.statistic.entities.OrderItemStat;
import com.geekbrains.spring.web.statistic.repositories.CartItemStatRepository;
import com.geekbrains.spring.web.statistic.repositories.OrderItemStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final OrderItemStatRepository orderItemStatRepository;
    private final CartItemStatRepository cartItemStatRepository;
    private final OrderItemStatConverter orderItemStatConverter;
    private final CartItemStatConverter cartItemStatConverter;

    public void addOrderItemsToStatistic(List<OrderItemDto> itemsDTO) {
        List<OrderItemStat> orderStatList = itemsDTO.stream().map(orderItemStatConverter::DtoToEntity).collect(Collectors.toList());
        orderItemStatRepository.saveAll(orderStatList);
    }

    public void addCartItemsToStatistic(List<ProductStatDto> productDtoList) {

        List<CartItemStat> cartStatList = productDtoList.stream().map(cartItemStatConverter::DtoToEntity).collect(Collectors.toList());
        cartItemStatRepository.saveAll(cartStatList);
    }





private Date getMe30DaysAgo(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-30);
        Date date=cal.getTime();
        return new java.sql.Date(date.getTime());

        }

public List<OrderItemStat> getTopProduct(){
        return orderItemStatRepository.getTop5ForLastMonth(getMe30DaysAgo());
        }
        }
