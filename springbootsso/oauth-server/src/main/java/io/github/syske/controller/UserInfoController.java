package io.github.syske.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @program: springbootsso
 * @description:
 * @author: syske
 * @create: 2019-12-13 10:04
 */
@RestController
public class UserInfoController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/user")
    public ResponseEntity<Object> getUser(Principal principal) {
        logger.info("principal:" + principal);
        return new ResponseEntity<Object>(principal, HttpStatus.OK);
    }
}
