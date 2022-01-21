package com.geekbrains.spring.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto> items;
    private int totalPrice;
//    private BigDecimal totalPrice;
    private String address;
    private String phone;
}
