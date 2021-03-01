package io.github.syske.springbootnacosdemo.controller;

import com.alibaba.druid.pool.DruidPooledConnection;
import io.github.syske.springbootnacosdemo.druidconfig.DruidDataSourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @program: springboot-nacos-demo
 * @description: 测试controller
 * @author: syske
 * @create: 2021-02-28 14:17
 */
@RestController
public class TestController {

    @Autowired
    private DruidDataSourceWrapper dataSourceWrapper;

    @RequestMapping("/test")
    public String testDruid() throws SQLException {
        DruidPooledConnection connection = dataSourceWrapper.getConnection();
        Statement statement = connection.createStatement();
        ResultSet  resultSet = statement.executeQuery("SELECT * from user;");
        System.out.println(resultSet);
        while(resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }
        return "hello druid";
    }
}
