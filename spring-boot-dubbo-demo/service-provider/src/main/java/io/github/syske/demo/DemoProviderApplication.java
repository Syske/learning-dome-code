package io.github.syske.demo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@DubboComponentScan
public class DemoProviderApplication {

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
    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "application.dubbo.application")
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        return applicationConfig;
    }

    @Bean
    @ConfigurationProperties(ignoreUnknownFields = false, prefix = "application.dubbo.registry")
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        return registryConfig;
    }

}
