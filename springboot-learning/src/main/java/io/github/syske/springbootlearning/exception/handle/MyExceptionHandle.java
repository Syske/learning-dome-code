package io.github.syske.springbootlearning.exception.handle;

import io.github.syske.springbootlearning.entity.Result;
import io.github.syske.springbootlearning.exception.MyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandle {

    @ExceptionHandler(MyException.class)
    public Result exceptionHandle(MyException e) {
        return Result.getFailed("system error:MyException" + e.getMessage(), null);
    }
}
