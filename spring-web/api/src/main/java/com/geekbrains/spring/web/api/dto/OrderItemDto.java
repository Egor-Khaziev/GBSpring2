package com.geekbrains.spring.web.api.dto;

import com.geekbrains.spring.web.api.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private int pricePerProduct;
    private int price;

//    public OrderItemDto(Product product) {
//        this.productId = product.getId();
//        this.productTitle = product.getTitle();
//        this.quantity = 1;
//        this.pricePerProduct = product.getPrice();
//        this.price = product.getPrice();
//    }

    public OrderItemDto(ProductDto productDto) {
        this.productId = productDto.getId();
        this.productTitle = productDto.getTitle();
        this.quantity = 1;
        this.pricePerProduct = productDto.getPrice();
        this.price = productDto.getPrice();
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.quantity * this.pricePerProduct;
    }
}
