package com.geekbrains.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String category;
    private int price;
//    private BigDecimal price;


    public ProductDto(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
