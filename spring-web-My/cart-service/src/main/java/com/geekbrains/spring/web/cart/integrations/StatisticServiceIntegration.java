package com.geekbrains.spring.web.cart.integrations;

import com.geekbrains.spring.web.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@RequiredArgsConstructor
public class StatisticServiceIntegration {

    private final RestTemplate restTemplate;
    private final CartService сartService;

    @Value("${integrations.statistic-service.url}")
    private String statisticServiceUrl;


    @Scheduled(fixedDelay = 300000)
    public void SendInfoToStatistic() {

        if(сartService.statisticList==null){сartService.statisticList=new ArrayList<>();}
        restTemplate.postForLocation(
                statisticServiceUrl + "/api/v1/cartStatistic", сartService.statisticList);

        сartService.statisticList =new ArrayList() {
        };
    }


}