package io.github.syske.mybatis.datasource.controller;

import io.github.syske.mybatis.datasource.datasource.vo.DataSourceHelper;
import io.github.syske.mybatis.datasource.entity.LoginInfoEntity;
import io.github.syske.mybatis.datasource.entity.MessageEntity;
import io.github.syske.mybatis.datasource.dao.LoginInfoMapper;
import io.github.syske.mybatis.datasource.dao.MessageMapper;
import io.github.syske.mybatis.datasource.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: spring-mybatis-dynamic-datasource-demo
 * @description: 数据源测试
 * @author: syske
 * @date: 2022-04-10 17:10
 */
@RestController
public class DataSourceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/ds/test/{ds-name}")
    public Object test(@PathVariable(name = "ds-name") String dsName) {
        if ("messageDb".equals(dsName)) {
            DataSourceHelper.changeToMessageDb();
            List<MessageEntity> messageEntities = messageMapper.selectAll();
            logger.info("messageEntities = {}", messageEntities);
        } else if ("loginDb".equals(dsName)){
            DataSourceHelper.changeToSpecificDataSource("1");
            List<LoginInfoEntity> loginInfoEntities = loginInfoMapper.selectAll();
            logger.info("loginInfoEntities = {}", loginInfoEntities);
        }
        return dsName;
    }
}
