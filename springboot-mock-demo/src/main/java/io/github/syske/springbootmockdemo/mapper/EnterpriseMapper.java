package io.github.syske.springbootmockdemo.mapper;

import io.github.syske.springbootmockdemo.service.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * enterprise
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-28 下午9:57
 */
@Component
public class EnterpriseMapper {

    @Autowired
    private MessageServiceImpl messageService;

    public int insertEnterprise(Long id) {
        System.out.println("保存enterprise：" + id);
        messageService.sendMessage("企业保存成功");
        return 1;
    }

    public String selectEnterprise(Long id) {
        System.out.println("查询企业成功:" + id);
        return "" + id;
    }
}
