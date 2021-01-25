package io.github.syske.could.controller;

/**
 * @program: spring-Cloud-dome
 * @description:
 * @author: syske
 * @create: 2019-12-11 17:52
 */
import io.github.syske.could.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EchoController {

    @Autowired
    EchoService echoService;

    @GetMapping(value = "/echo")
    public String hi(@RequestParam String name) {
        return echoService.echo( name );
    }
}