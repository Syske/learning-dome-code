/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springwebfluxdemo.handler;

import io.github.syske.springwebfluxdemo.repository.UserRepository;
import io.github.syske.springwebfluxdemo.repository.entity.User;
import io.github.syske.springwebfluxdemo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author syske
 * @version 1.0
 * @date 2021-07-30 8:51
 */
@Service
public class UserHandler implements HandlerFunction {

    @Autowired
    private UserRepository userRepository;

    public Mono<ServerResponse> getUser(ServerRequest request) {
        String idStr = request.pathVariable("/id");
        Long id = Long.valueOf(idStr);
        Mono<UserVo> result = userRepository.findById(id).map(u -> translate(u));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(result, UserVo.class);
    }

    private UserVo translate(User user) {
        return new UserVo();
    }

    @Override
    public Mono handle(ServerRequest serverRequest) {
        return null;
    }
}
