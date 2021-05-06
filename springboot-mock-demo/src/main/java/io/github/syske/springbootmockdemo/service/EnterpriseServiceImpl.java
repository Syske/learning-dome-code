package io.github.syske.springbootmockdemo.service;

import io.github.syske.springbootmockdemo.mapper.EnterpriseMapper;
import io.github.syske.springbootmockdemo.mapper.MessageMapper;
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
public class EnterpriseServiceImpl {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private UserServiceImpl userService;

    public String saveEnterpriseData(Long id, String userId, List<String> strs) {

        String enterprise = enterpriseMapper.selectEnterprise(id);
        if (!"admin".equals(enterprise)) {
            System.out.println("企业不存在");
            return "企业不存在";
        }
        int insertEnterprise = enterpriseMapper.insertEnterprise(id);
        int saveUser = userService.saveUser(userId);
        return "hello" + insertEnterprise + saveUser + strs;

    }
}
