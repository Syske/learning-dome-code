package io.github.syske.springbootmockdemo;

import io.github.syske.springbootmockdemo.mapper.MockMapper;
import io.github.syske.springbootmockdemo.service.MockServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

/**
 * unit test
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-27 下午11:13
 */
@RunWith(MockitoJUnitRunner.class)
public class MockServiceTest {

    @InjectMocks
    private MockServiceImpl mockService;

    @Mock
    private MockMapper mockMapper;

    @Test
    public void test() {
        ArrayList<String> ls = new ArrayList<>();
        ls.add("sdfsdf");
        given(mockMapper.listStrs(anyLong())).willReturn(ls);
        mockService.sayHello(any(), any(), any());
    }
}
