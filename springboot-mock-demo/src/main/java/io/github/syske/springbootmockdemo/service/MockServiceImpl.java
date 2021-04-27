package io.github.syske.springbootmockdemo.service;

import io.github.syske.springbootmockdemo.mapper.MockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Mockservice
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-27 下午11:29
 */
@Service
public class MockServiceImpl {

    @Autowired
    private MockMapper mockMapper;

    public String sayHello(Long id, String userId, List<String> strs) {
        mockMapper.listStrs(id);
        return "hello" + id + userId + strs;

    }
}
