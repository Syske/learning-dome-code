package io.github.syske.springbootmockdemo;

import io.github.syske.springbootmockdemo.mapper.EnterpriseMapper;
import io.github.syske.springbootmockdemo.mapper.MessageMapper;
import io.github.syske.springbootmockdemo.service.EnterpriseServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

/**
 * unit test
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-27 下午11:13
 */
@RunWith(MockitoJUnitRunner.class)
public class EnterpriseServiceTest {

    @InjectMocks
    private EnterpriseServiceImpl enterpriseService;

    @Mock
    private EnterpriseMapper enterpriseMapper;

    @Test
    public void test() {
        ArrayList<String> ls = new ArrayList<>();
        ls.add("sdfsdf");
//        given(enterpriseMapper.selectEnterprise(12323L)).willReturn("admin");
        given(enterpriseMapper.selectEnterprise(anyLong())).willReturn("admin");
        enterpriseService.saveEnterpriseData(12323L, "testets", ls);
        Assert.assertEquals();
        Assert.assertFalse();
        Assert.assertTrue();
        Assert.assertNull();
    }
}
