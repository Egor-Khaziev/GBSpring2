package com.geekbrains.spring.web.statistic.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orderitemstat")
@Data
@NoArgsConstructor
public class OrderItemStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "productid")
    private Long productId;

    @Column(name = "producttitle")
    private String productTitle;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "date")
    private Date date;



    public OrderItemStat(Long productId, String productTitle, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.date = new Date();
    }
}
