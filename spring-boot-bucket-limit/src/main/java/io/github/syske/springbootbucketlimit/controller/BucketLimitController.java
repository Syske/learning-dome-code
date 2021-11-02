/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootbucketlimit.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.syske.springbootbucketlimit.annotation.LeakyBucketLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;


/**
 * @author syske
 * @version 1.0
 * @date 2021-11-01 8:49
 */
@RestController
public class BucketLimitController {
    private static final Logger logger = LoggerFactory.getLogger(BucketLimitController.class);

    @LeakyBucketLimit(limitBeanName = "leakyBucket")
    @GetMapping("/bucket")
    public Object bucketTest() {
        JSONObject result = new JSONObject();
        result.put("result", "请求成功");
        logger.info("timestamp: {}, result: {}", System.currentTimeMillis(), result);
        return result;
    }

    private void bucket() {
        LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>(100);
        BucketRejectedExecutionHandler bucketRejectedExecutionHandler = new BucketRejectedExecutionHandler();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 1L, TimeUnit.SECONDS, tasks, bucketRejectedExecutionHandler);

    }

    static class BucketRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            logger.info("已达到访问限制上限，丢弃相关请求");
        }
    }
}
