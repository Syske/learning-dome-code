package io.github.syske.springbootjwtdemo.exception;

import io.github.syske.springbootjwtdemo.dao.entity.ReturnEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-10 00:01
 */
@RestControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    public ReturnEntity handleException(Exception e) {
        logger.warn("错误信息:" + e);
        if (e instanceof AuthorizationException) {
            return ReturnEntity.failedResult(1, "未知错误");
        } else {
            return ReturnEntity.failedResult(2, "未知错误");
        }
    }
}
