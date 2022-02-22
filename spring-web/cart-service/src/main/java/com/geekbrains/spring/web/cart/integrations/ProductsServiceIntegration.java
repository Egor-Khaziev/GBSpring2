package com.geekbrains.spring.web.cart.integrations;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.CartServiceAppError;
import com.geekbrains.spring.web.cart.exceptions.ProductServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient productServiceWebClient;

    public Optional<ProductDto> findById(Long id) {
        ProductDto productDto = productServiceWebClient.get()
                        .uri("/api/v1/products/" + id)
                        .retrieve()
                        .bodyToMono(ProductDto.class)
                        .block();

        return Optional.ofNullable(productDto);
    }

}
