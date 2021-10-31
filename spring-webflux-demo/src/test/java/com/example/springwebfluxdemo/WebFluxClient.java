package com.example.springwebfluxdemo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.function.Consumer;

/**
 * @program: spring-webflux-demo
 * @description: 客户端
 * @author: syske
 * @date: 2021-07-29 13:00
 */
public class WebFluxClient {
    public static void main(String[] args) {
        WebClient client = WebClient.create("http://localhost:8999");
        Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
                .acceptCharset(Charset.defaultCharset())
                .header("token:1234567")
//                .contentType(MediaType.APPLICATION_JSON)
                .cookie("syske", "yyds")
                .attribute("name", "syske")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        String block = stringMono.block();
        System.out.println("返回结果：" + block);

        Mono<String> stringMono2 = client.post()
                .uri("/webflux/hi2")
                .acceptCharset(Charset.defaultCharset())
                .header("token:1234567")
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT_CHARSET, "UTF-8");
                })
                .contentType(MediaType.APPLICATION_JSON)
                .cookie("syske", "yyds")
                .attribute("name", "syske")
//                .body(Mono.just("{\"name\": \"syske\"}"), String.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        String block1 = stringMono2.block();
        System.out.println("block1" + block1);
    }
}
