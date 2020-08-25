package io.github.syske.springbootlearning.controller.service;

import io.github.syske.springbootlearning.entity.Result;
import io.github.syske.springbootlearning.exception.MyException;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public Result test3() {
        throw new MyException("service test3");
    }
}
