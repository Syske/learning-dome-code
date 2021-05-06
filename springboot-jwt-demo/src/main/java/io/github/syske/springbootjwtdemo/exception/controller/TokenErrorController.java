package io.github.syske.springbootjwtdemo.exception.controller;

import io.github.syske.springbootjwtdemo.dao.entity.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot-jwt-demo
 * @description: 集中处理filter中的异常信息
 * @author: syske
 * @create: 2020-03-20 23:13
 */
@RestController
@RequestMapping(value = "error")
public class TokenErrorController extends BasicErrorController {

    public TokenErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }

    @Override
    @RequestMapping(produces = {MediaType.ALL_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return new ResponseEntity<>(ReturnEntity.failedResultMap(1, "未知错误"), HttpStatus.OK);
    }

}
