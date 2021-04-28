package io.github.syske.springbootdruidmoredatasources.controller;

/**
 * @program: springboot-druid-more-datasources
 * @description:
 * @author: syske
 * @create: 2020-04-21 17:02
 */

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * springboot druiddatasource案例
 *
 * @author ouyangjun
 */
@RestController
@RequestMapping(value = "/druiddatasource")
public class DruidController {

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    @Qualifier("oneBackDataSource")
    private DataSource oneBackDataSource;

    @Autowired
    @Qualifier("twoBackDataSource")
    private DataSource twoBackDataSource;

    /**
     * 启动之后,访问该接口,然后在druid界面查看数据源使用相关信息
     * 访问地址: http://localhost:8080/druiddatasource/index
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        try {
            Connection primaryConnection = primaryDataSource.getConnection();
            System.out.println("==>primaryConnection: " + primaryConnection);

            Connection oneBackConnection = oneBackDataSource.getConnection();
            System.out.println("==>oneBackConnection: " + oneBackConnection);

            Connection twoBackConnection = twoBackDataSource.getConnection();
            System.out.println("==>twoBackConnection: " + twoBackConnection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Hello oysept druiddatasource!";
    }

}
