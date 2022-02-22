package com.geekbrains.spring.web.api.carts;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель продукта в корзине")
public class CartItemDto {

    @Schema(name = "ID продукта", required = true, example = "1")
    private Long productId;

    @Schema(name = "название продукта", required = true, maxLength = 255, minLength = 3, example = "Апельсин")
    private String productTitle;

    @Schema(name = "количество", required = true, example = "2")
    private int quantity;

    @Schema(name = "цена за ед.", required = true, example = "60")
    private BigDecimal pricePerProduct;

    @Schema(name = "цена", required = true, example = "120")
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String productTitle, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }
}
