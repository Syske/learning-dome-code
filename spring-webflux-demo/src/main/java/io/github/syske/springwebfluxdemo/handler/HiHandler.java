/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springwebfluxdemo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * hi服务处理器
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-28 8:46
 */
@Component
public class HiHandler {
    public Mono<ServerResponse> sayHi(ServerRequest request) {
        Optional<Object> name = request.attribute("name");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"message\": \"Hi , this is SpringWebFlux, " + name.orElse("null") + "\"}"));
    }
}
