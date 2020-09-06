package io.github.syske.pdfview.exception;

import io.github.syske.pdfview.entity.AjaxJson;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: pdf-view
 * @description: 异常处理
 * @author: syske
 * @create: 2020-06-30 14:38
 */

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(Exception.class)
    public AjaxJson exceptionHandler(Exception e) {
        return new AjaxJson( e);
    }
}
