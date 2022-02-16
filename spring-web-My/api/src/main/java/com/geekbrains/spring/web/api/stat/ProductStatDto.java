package com.geekbrains.spring.web.api.stat;


public class ProductStatDto {
    private Long id;
    private String title;
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductStatDto() {
    }

    public ProductStatDto(Long id, String title, Integer quantity) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
    }
}
