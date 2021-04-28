package io.github.syske.springbootmockdemo.service;

import io.github.syske.springbootmockdemo.mapper.EnterpriseMapper;
import io.github.syske.springbootmockdemo.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * mock2
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-28 下午9:51
 */
@Service
public class MessageServiceImpl {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private EnterpriseServiceImpl messageService;
    @Autowired
    private EnterpriseMapper enterpriseMapper;



    public String sendMessage(String message) {
        messageMapper.insert(message);
        return "success";
    }
}
