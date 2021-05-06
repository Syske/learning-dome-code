package io.github.syske.springbootmockdemo.service;

import io.github.syske.springbootmockdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * user service
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-28 下午10:04
 */
@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageServiceImpl messageService;

    public int saveUser(String userId) {
        if ("admin".equals(userMapper.selectUser(userId))) {
            System.out.println("用户已存在");
            return -1;
        }
        int i = userMapper.intsertUser(userId);
        String sendMessage = messageService.sendMessage("用户保存成功");
        System.out.println("发送消息成功：" + sendMessage);
        return 2 + i;
    }
}
