/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springwebfluxdemo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

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
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(BodyInserters.fromValue("{\"message\": \"Hi , this is SpringWebFlux, " + name.orElse("null") + "\"}"));
    }

    public Mono<ServerResponse> sayHi2(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Hi , this is SpringWebFlux 2222222222222222"));
    }

    public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(  // 1
                Flux.interval(Duration.ofSeconds(1)).   // 2
                        map(new Function<Long, String>() {
                    @Override
                    public String apply(Long l) {
                        System.out.println(l);
                        return new SimpleDateFormat("HH:mm:ss").format(new Date());
                    }
                }),
                String.class);
    }

}
