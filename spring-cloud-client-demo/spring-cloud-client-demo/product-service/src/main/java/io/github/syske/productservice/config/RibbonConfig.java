/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.productservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ribbon
 *
 * @author syske
 * @version 1.0
 * @date 2021-08-02 8:19
 */
@Configuration
public class RibbonConfig {
    // 多节点负载
    @LoadBalanced
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
