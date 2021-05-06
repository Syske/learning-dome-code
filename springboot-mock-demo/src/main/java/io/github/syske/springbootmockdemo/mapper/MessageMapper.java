package io.github.syske.springbootmockdemo.mapper;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * mapper
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-27 下午11:34
 */
@Component
public class MessageMapper {
    public List<String> listStrs(Long id) {
        return new ArrayList();
    }

    public String insert(String data) {
        System.out.println("保存数据");
        return data;
    }
}
