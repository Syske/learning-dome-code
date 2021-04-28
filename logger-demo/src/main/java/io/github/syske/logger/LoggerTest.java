package io.github.syske.logger;

import ch.qos.logback.classic.LoggerContext;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class LoggerTest {
    private Logger log4j = Logger.getLogger(this.getClass());
    ch.qos.logback.classic.Logger logback1 = new LoggerContext().getLogger(this.getClass());
    org.slf4j.Logger logback2 = new LoggerContext().getLogger(this.getClass());
    org.slf4j.Logger logback = LoggerFactory.getLogger(this.getClass());


    @Test
    public void log4jTest() {
        log4j.trace("log4j trace……");
        log4j.debug("log4j debug……");
        log4j.info("log4j info");
        log4j.warn("log4j warn……");
        log4j.error("log4j error……");
        log4j.fatal("log4j fatal……");
    }

    @Test
    public void logbackTest() {
        logback.trace("logback trace……");
        logback.debug("logback debug……");
        logback.info("logback info");
        logback.warn("logback warn……");
        logback.error("logback error……");
    }

    @Test
    public void slf4jTest() {
        logback.error("错误信息：{}，错误原因：{}，错误类型：{}", "系统查询异常", "网络异常", "致命错误");
        log4j.error("错误信息：" + "系统查询异常" + "，错误原因：" + "网络异常" + "，错误类型：" + "致命错误");
    }

}
