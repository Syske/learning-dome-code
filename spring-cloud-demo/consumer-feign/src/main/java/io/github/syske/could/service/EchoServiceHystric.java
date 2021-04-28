package io.github.syske.could.service;

import org.springframework.stereotype.Component;

/**
 * @program: spring-Cloud-dome
 * @description:
 * @author: syske
 * @create: 2019-12-11 18:26
 */

@Component
public class EchoServiceHystric implements EchoService {

    @Override
    public String echo(String name) {
        return "sorry service is error;";
    }

}