/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springwebfluxdemo.config;

import io.github.syske.springwebfluxdemo.handler.HiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 路由设置
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-28 8:49
 */
@Configuration
public class RouteConfig {
    @Bean
    public RouterFunction<ServerResponse> routSayHi(HiHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("webflux/hi")
                        .and(RequestPredicates.accept(MediaType.ALL)), handler::sayHi);
    }
}
