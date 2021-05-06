package io.github.syske.springbootnacosdemo;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author syske
 * @Description nacos配置
 * @Date
 * @Param
 * @return
 **/

@SpringBootApplication
@NacosPropertySource(dataId = "spring-boot-nacos-demo", autoRefreshed = true)
public class SpringBootNacosDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNacosDemoApplication.class, args);
    }

}
