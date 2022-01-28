package com.flamexander.cloud.front.service;

import com.flamexander.cloud.common.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RestController   //Если убрать Рест контроллер то сыпется с ошибками
public class FrontApp {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {return new RestTemplate();}
    
    public static void main(String[] args) {SpringApplication.run(FrontApp.class, args);}


    // http://localhost:3000/index.html
    @GetMapping("/api/v1/products")
    public List<ProductDto> getProducts(){
        //List<ProductDto> productsList =  restTemplate.getForObject("http://localhost:51919/api/v1/products", List.class);
        List<ProductDto> productsList =  restTemplate.getForObject("http://product-service/api/v1/products", List.class);
        return productsList;
    }



}
