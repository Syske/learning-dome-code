package io.github.syske.apigatewaydemo.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: api-gateway-demo
 * @description: 路由配置
 * @author: syske
 * @create: 2020-05-20 15:05
 */
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/test/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://127.0.0.1:8720"))
                .build();
    }
}
