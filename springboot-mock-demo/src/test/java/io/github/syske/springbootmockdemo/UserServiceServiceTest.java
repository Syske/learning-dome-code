package io.github.syske.springbootmockdemo;

import io.github.syske.springbootmockdemo.mapper.UserMapper;
import io.github.syske.springbootmockdemo.service.MessageServiceImpl;
import io.github.syske.springbootmockdemo.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * test
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-28 下午10:14
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private MessageServiceImpl messageService;

    @Test
    public void saveUserTest1() {
        String userId = "test2312";
        given(userMapper.selectUser(anyString())).willReturn("admin");
        int saveUser1 = userService.saveUser(userId);
        assertEquals(saveUser1, -1);
    }

    @Test
    public void saveUserTest2() {
        String userId = "test2312";
        given(userMapper.intsertUser(anyString())).willReturn(2);
        given(messageService.sendMessage(anyString())).willReturn("user insert success");
        int saveUser2 = userService.saveUser(userId);
        assertEquals(saveUser2, 4);
    }
}
