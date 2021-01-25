package io.github.syske.springbootdemo.exception;

import io.github.syske.springbootdemo.entity.response.WrapperResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @program: ydjyint01service
 * @description: 异常处理
 * @author: syske
 * @create: 2020-09-10 10:36
 */
@ControllerAdvice
public class ExceptionHandle {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WrapperResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        return new WrapperResponse().error(e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
