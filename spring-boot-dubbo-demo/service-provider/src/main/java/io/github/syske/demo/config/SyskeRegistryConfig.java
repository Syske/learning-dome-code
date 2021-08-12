package io.github.syske.demo.config;

import org.apache.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: spring-boot-dubbo-demo
 * @description: registry config
 * @author: syske
 * @date: 2021-08-12 13:14
 */
@Component
@EnableConfigurationProperties(SyskeRegistryConfig.class)
@ConfigurationProperties(prefix = "application.dubbo.registry.server", ignoreInvalidFields = true)
public class SyskeRegistryConfig {
    public String address;

    public String username;

    public String password;

    public Integer port;

    public String protocol;

    public String transporter;

    public String server;

    public String client;

    public String cluster;

    public String zone;

    public String group;

    public String version;

    public Integer timeout;

    public Integer session;
    
    public String file;

    public Integer wait;

    public Boolean check;

    public Boolean dynamic;

    public Boolean register;

    public Boolean subscribe;

    public Map<String, String> parameters;

    public Boolean isDefault;

    public Boolean simplified;

    public String extraKeys;

    public Boolean useAsConfigCenter;

    public Boolean useAsMetadataCenter;

    public String accepts;

    public Boolean preferred;

    public Integer weight;
}
