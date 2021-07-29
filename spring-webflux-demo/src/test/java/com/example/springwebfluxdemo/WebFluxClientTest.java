/* Copyright © 2021 syske. All rights reserved. */
package com.example.springwebfluxdemo;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * webflux-client
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-29 7:53
 */
public class WebFluxClientTest {
    public static void main(String[] args) {
        WebClient client = WebClient.create("http://localhost:8999");
        // 定义POST请求
        Mono<String> serverResponseMono = client.get()
                // 设置请求URI
                .uri("/webflux/hi")
                // 请求体内容
                .attribute("name", "syske")
                // 设定响应体类型
                .accept(MediaType.APPLICATION_JSON)
                // 设置请求结果检索规则
                .retrieve()
                // 将结果体转换为Mono封装的数据流
                .bodyToMono(String.class);
        String block = serverResponseMono.block();
        System.out.println("服务器响应数据：" + block);

       /* Mono<ServerResponse> serverResponseMono = client.post()
                // 设置请求URI
                .uri("/webflux/hi")
                // 请求体content-type
                .contentType(MediaType.APPLICATION_JSON)
                // 请求体内容
                .body(Mono.just("{\"name\": \"syske\"}"), ServerResponse.class)
                // 设定响应体类型
                .accept(MediaType.APPLICATION_JSON)
                // 设置请求结果检索规则
                .retrieve()
                // 将结果体转换为Mono封装的数据流
                .bodyToMono(ServerResponse.class);*/
    }
}
