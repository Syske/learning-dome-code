/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springwebfluxdemo.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebHandler;

///**
// * webflux配置
// *
// * @author syske
// * @version 1.0
// * @date 2021-07-28 7:57
// */
//@Configuration
//@ComponentScan
//@EnableWebFlux
//public class WebFluxConfig implements WebFluxConfigurer {
//    @Bean
//    public WebHandler webHandler(ApplicationContext applicationContext) {
//        DispatcherHandler dispatcherHandler = new DispatcherHandler(applicationContext);
//        return dispatcherHandler;
//    }
//
//
//}
