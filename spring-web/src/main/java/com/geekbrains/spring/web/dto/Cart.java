package com.geekbrains.spring.web.dto;

import com.geekbrains.spring.web.entities.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (addProduct(product.getId())) {
            return;
        }
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public boolean addProduct(Long id) {
        for (OrderItemDto oid : items) {
            if (oid.getProductId().equals(id)) {
                oid.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void decreaseProduct(Long id) {
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItemDto o = iter.next();
            if (o.getProductId().equals(id)) {
                o.changeQuantity(-1);
                if (o.getQuantity() <= 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void removeProduct(Long id) {
        items.removeIf(o -> o.getProductId().equals(id));
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (OrderItemDto oid: items) {
            totalPrice = totalPrice.add(oid.getPrice());
        }
    }

    public void clearProduct(Long id) {
        for (OrderItemDto oid: items){
            if (oid.getProductId()==(id)){
                items.remove(oid);
                recalculate();
                return;
            }
        }
    }

    public boolean reduceProduct(Long id) {
        for (OrderItemDto oid: items) {
            if(oid.getProductId()==(id)){

                if(oid.getQuantity()>1){
                    oid.changeQuantity(-1);
                } else {
                    items.remove(oid);
                }

                recalculate();
                return true;
            }
        }
        return false;
    }
}
