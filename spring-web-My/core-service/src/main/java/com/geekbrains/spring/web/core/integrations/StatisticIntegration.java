package com.geekbrains.spring.web.core.integrations;

import com.geekbrains.spring.web.core.converters.OrderItemConverter;
import com.geekbrains.spring.web.core.entities.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StatisticIntegration {

    private final RestTemplate restTemplate;
    private final OrderItemConverter orderItemConverter;

    @Value("${integrations.statistic-service.url}")
    private String statisticServiceUrl;

    // TODO: нужна синхронизация
    private List<OrderItem> items ;


    // TODO: нужна синхронизация
    public void toSendOrderInfoToStatistic(List<OrderItem> newItems) {
        if (items==null) {items = new ArrayList<>();}

        for (OrderItem oi : newItems) {
            if (items.contains(oi)){
                int index = items.indexOf(oi);

                OrderItem tempItem = items.get(index);
                tempItem.setQuantity(tempItem.getQuantity()+oi.getQuantity());
                tempItem.setPrice(tempItem.getPricePerProduct()*tempItem.getQuantity());

                items.set(index, tempItem);
            }
            else {
                items.add(oi);
            }
        }

        SendInfoToStatistic();

    }


    public void SendInfoToStatistic() {

        restTemplate.postForLocation(
                statisticServiceUrl + "/api/v1/cartStatistic",
                items.stream().map(orderItemConverter::entityToDto).collect(Collectors.toList())
        );
        items=new ArrayList<>();
    }


}


