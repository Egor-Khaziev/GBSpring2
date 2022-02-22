package com.geekbrains.spring.web.cart.configs;

import com.geekbrains.spring.web.cart.properties.ProductServiceIntegrationProperties;
import com.geekbrains.spring.web.cart.properties.ProductServiceIntegrationSubProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(
        {ProductServiceIntegrationProperties.class, ProductServiceIntegrationSubProperties.class}
)
@RequiredArgsConstructor
public class AppConfig {
    private final ProductServiceIntegrationProperties productServiceIntegrationProperties;
    private final ProductServiceIntegrationSubProperties productServiceIntegrationSubProperties;

    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productServiceIntegrationSubProperties.getConnect())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(productServiceIntegrationSubProperties.getRead(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(productServiceIntegrationSubProperties.getWrite(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(productServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
