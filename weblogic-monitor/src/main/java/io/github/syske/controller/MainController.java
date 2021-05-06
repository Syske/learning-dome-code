package io.github.syske.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: weblogic-monitor
 * @description:
 * @author: syske
 * @create: 2020-01-16 17:07
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }


}
