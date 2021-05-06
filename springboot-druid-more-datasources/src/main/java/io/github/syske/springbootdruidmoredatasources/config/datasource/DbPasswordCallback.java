package io.github.syske.springbootdruidmoredatasources.config.datasource;

/**
 * @program: springboot-druid-more-datasources
 * @description: 密码回调函数
 * @author: syske
 * @create: 2020-04-21 19:26
 */

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.util.DruidPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class DbPasswordCallback extends DruidPasswordCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbPasswordCallback.class);

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String password = (String) properties.get("password");
        String publickey = (String) properties.get("publickey");
        try {
            String dbpassword = ConfigTools.decrypt(publickey, password);
            setPassword(dbpassword.toCharArray());
        } catch (Exception e) {
            LOGGER.error("Druid ConfigTools.decrypt", e);
        }
    }
}