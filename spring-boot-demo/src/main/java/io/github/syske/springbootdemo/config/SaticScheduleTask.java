package io.github.syske.springbootdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @program: spring-boot-demo
 * @description: 定时任务
 * @author: syske
 * @create: 2020-01-17 11:40
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    private Logger logger = LoggerFactory.getLogger(SaticScheduleTask.class);


    /**
     * 每120秒执行一次
     */
    @Scheduled(fixedRate = 120000)
    public void getServerData() {
        logger.info("系统运行时间:{}", new Date());

    }
}
