/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springwebfluxdemo.config;

import io.github.syske.springwebfluxdemo.handler.HiHandler;
import io.github.syske.springwebfluxdemo.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 过滤器配置
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-30 7:54
 */
@Configuration
public class FilterConfig {

    private UserHandler userHandler = null;

    private String HEADER_NAME = "header_name";
    private String HEADER_PASSWORD = "header_password";

//    @Bean
    public RouterFunction<ServerResponse> securityRouter() {
    RouterFunction<ServerResponse> routerFunction = route(
            GET("/security/user/{id}")
    .and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
            .filter((request, next) -> filterLogic(request, next));
    return routerFunction;
    }

    private Mono<ServerResponse> filterLogic(ServerRequest request, HandlerFunction<ServerResponse> next) {
        String username = request.headers().header(HEADER_NAME).get(0);
        String password = request.headers().header(HEADER_PASSWORD).get(0);

        if (!Objects.equals(username, password)) {
            return next.handle(request);
        }

        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();

    }
}
