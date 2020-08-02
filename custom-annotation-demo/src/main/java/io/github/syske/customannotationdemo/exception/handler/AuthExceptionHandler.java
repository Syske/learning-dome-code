package io.github.syske.customannotationdemo.exception.handler;

import io.github.syske.customannotationdemo.entity.ResultJson;
import io.github.syske.customannotationdemo.exception.AuthException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler{
    @ExceptionHandler(AuthException.class)
    public ResultJson exception() {
        ResultJson resultJson = new ResultJson();
        resultJson.setError(401, "没有访问权限");
        return resultJson;
    }
}
