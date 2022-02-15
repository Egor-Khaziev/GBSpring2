package com.geekbrains.spring.web.statistic.controllers;

import com.geekbrains.spring.web.api.core.OrderItemDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.stat.ProductStatDto;
import com.geekbrains.spring.web.statistic.entities.OrderItemStat;
import com.geekbrains.spring.web.statistic.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping("/orderStatistic")
    public void addOrderItemsToStatistic(@RequestBody List<OrderItemDto> orderItemDto) {
        statisticService.addOrderItemsToStatistic(orderItemDto);
    }

    @PostMapping("/cartStatistic")
    public void addCartItemsToStatistic(@RequestBody List<ProductStatDto> productDtoList) {
        statisticService.addCartItemsToStatistic(productDtoList);
    }

    @GetMapping("/getTop")
    public List<OrderItemStat> getTopProduct() {
        return statisticService.getTopProduct();
    }
}
