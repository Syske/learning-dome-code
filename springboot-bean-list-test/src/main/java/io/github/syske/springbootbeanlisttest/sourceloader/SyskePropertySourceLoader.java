package io.github.syske.springbootbeanlisttest.sourceloader;

import com.google.common.collect.Lists;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @program: springboot-bean-list-test
 * @description: 资源加载器
 * @author: syske
 * @date: 2022-06-23 17:53
 */
public class SyskePropertySourceLoader implements PropertySourceLoader {
    @Override
    public String[] getFileExtensions() {
        return new String[] {"properties"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        System.out.println("加载配置文件……");
        List<PropertySource<?>> propertySourceList = Lists.newArrayList();
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        properties.setProperty("server.port", "8088");
        PropertySource propertySource = new PropertiesPropertySource("test", properties);
        propertySourceList.add(propertySource);

        return propertySourceList;
    }
}
