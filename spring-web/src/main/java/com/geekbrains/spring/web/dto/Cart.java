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
    private int totalPrice;
//    private BigDecimal totalPrice;

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

    public void removeProduct(Long id) {
        for (OrderItemDto oid: items){
            if (oid.getProductId()==(id)){
                items.remove(oid);
                recalculate();
                return;
            }
        }
    }

    public void decrement(Long productId) {
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItemDto o = iter.next();
            if (o.getProductId().equals(productId)) {
                o.changeQuantity(-1);
                if (o.getQuantity() <= 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }
        /// еще вариант decrement
//    public boolean reduceProduct(Long id) {
//        for (OrderItemDto oid: items) {
//            if(oid.getProductId()==(id)){
//
//                if(oid.getQuantity()>1){
//                    oid.changeQuantity(-1);
//                } else {
//                    items.remove(oid);
//                }
//
//                recalculate();
//                return true;
//            }
//        }
//        return false;
//    }


    public void clear() {
        items.clear();
        totalPrice = 0;
//        totalPrice = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = 0;
        for (OrderItemDto oid: items) {
            totalPrice =+ oid.getPrice();
        }
//        totalPrice = BigDecimal.ZERO;
//        for (OrderItemDto oid: items) {
//            totalPrice = totalPrice.add(oid.getPrice());
//        }
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



    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            boolean merged = false;
            for (OrderItemDto myItem : items) {
                if (myItem.getProductId().equals(anotherItem.getProductId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }
}
