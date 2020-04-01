package io.github.syske.could.controller;

/**
 * @program: spring-Cloud-dome
 * @description:
 * @author: syske
 * @create: 2019-12-11 18:04
 */
import io.github.syske.could.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EchoController {


    @Autowired
    private EchoService echoService;

    @GetMapping(value = "/echo")
    public String sayHi(@RequestParam String name) {
        return echoService.echo( name );
    }
}
