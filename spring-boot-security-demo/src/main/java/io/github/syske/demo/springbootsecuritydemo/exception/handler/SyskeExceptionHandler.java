/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.springbootsecuritydemo.exception.handler;

import com.google.common.collect.Maps;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 异常处理类
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-22 23:13
 */
@RestControllerAdvice
@RequestMapping(value = "error")
public class SyskeExceptionHandler extends BasicErrorController {

    public SyskeExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }

    @Override
    @RequestMapping(produces = {MediaType.ALL_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> result = Maps.newHashMap();
        HttpStatus status = this.getStatus(request);
        result.put("code", -1);
        result.put("message", "未知错误");
        result.put("success", false);
        result.put("status", status.value());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);
        response.setStatus(status.value());
        return new ModelAndView(String.valueOf(status.value()));
    }
}
