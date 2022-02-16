package com.geekbrains.spring.web.statistic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cartitemstat")
@Data
@NoArgsConstructor
public class CartItemStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "productTitle")
    private String productTitle;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "date")
    private Date date;



    public CartItemStat(Long productId, String productTitle, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.date = new Date();
    }


}
