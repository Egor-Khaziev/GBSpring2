package com.geekbrains.spring.web.statistic.converters;

import com.geekbrains.spring.web.api.core.OrderItemDto;
import com.geekbrains.spring.web.statistic.entities.OrderItemStat;
import org.springframework.stereotype.Component;

@Component
public class OrderItemStatConverter {

    public OrderItemStat DtoToEntity(OrderItemDto orderItemDto){

        OrderItemStat orderItemStat = new OrderItemStat(
                orderItemDto.getProductId(),
                orderItemDto.getProductTitle(),
                orderItemDto.getQuantity()
        );

        return orderItemStat;
    }

    public OrderItemDto EntityToDto(OrderItemStat orderItemStat){
        OrderItemDto oderItemDto = new OrderItemDto(
                orderItemStat.getProductId(),
                orderItemStat.getProductTitle(),
                orderItemStat.getQuantity(),
                0,
                0
        );

        return oderItemDto;
    }

}
