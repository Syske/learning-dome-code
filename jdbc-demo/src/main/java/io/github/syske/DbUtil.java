package io.github.syske;

/**
 * @program: jdbc-demo
 * @description:
 * @author: syske
 * @create: 2020-02-18 14:44
 */

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库的工具类
 */
public class DbUtil {

    static Logger log = LoggerFactory.getLogger(DbUtil.class);

    public static Connection getConnection() throws Exception {
        //得到类加载对象
        ClassLoader cl = DbUtil.class.getClassLoader();
        //通过类加载器对象得到指定的资源文件字节流
        InputStream is = cl.getResourceAsStream("jdbc.properties");
        Properties dbconfig = new Properties();
        dbconfig.load(is);
        Connection conn = null;
        Class.forName(dbconfig.getProperty("jdbc.driver"));
        String url = dbconfig.getProperty("jdbc.url");
        String user = dbconfig.getProperty("jdbc.user");
        String pwd = dbconfig.getProperty("jdbc.password");
        conn = DriverManager.getConnection(url, user, pwd);
        log.info("现在已连接MySQL数据库");
        return conn;
    }

    public static void closeConnection(Connection conn, PreparedStatement pre, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pre != null) {
                pre.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("关闭MySQL数据库");
    }
}
