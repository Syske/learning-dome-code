package io.github.syske.demo;

import io.github.syske.demo.config.SyskeRegistryConfig;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@DubboComponentScan
//@DubboComponentScan(value = "io.github.syske.demo.service.facade")
public class DemoProviderApplication {

    @Autowired
    private SyskeRegistryConfig syskeRegistryConfig;

    public static void main(String[] args) {
        SpringApplication.run(DemoProviderApplication.class, args);
    }

   /* @Bean
    public ConfigCenterConfig configCenterConfig() {
        ConfigCenterConfig configCenter = new   ConfigCenterConfig();
        configCenter.setAddress("zookeeper://127.0.0.1:2181");
        return configCenter;
    }*/

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-server");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setClient(syskeRegistryConfig.client);
        registryConfig.setAccepts(syskeRegistryConfig.address);
        return registryConfig;
    }

}
