/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootbucketlimit.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.syske.springbootbucketlimit.annotation.LeakyBucketLimit;
import io.github.syske.springbootbucketlimit.bucket.LeakyBucket;
import io.github.syske.springbootbucketlimit.util.BeanTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-01 22:12
 */
@Component
public class LeakyBucketLimiterInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 判断方法是否包含CounterLimit，有这个注解就需要进行限速操作
            if (handlerMethod.hasMethodAnnotation(LeakyBucketLimit.class)) {
                LeakyBucketLimit annotation = handlerMethod.getMethod().getAnnotation(LeakyBucketLimit.class);
                LeakyBucket leakyBucket = (LeakyBucket)BeanTool.getBean(annotation.limitClass());
                boolean acquire = leakyBucket.acquire();
                response.setContentType("text/json;charset=utf-8");
                JSONObject result = new JSONObject();
                if (acquire) {
                    result.put("result", "请求成功");
                } else {
                    result.put("result", "达到漏桶上限，禁止访问");
                    response.getWriter().print(JSON.toJSONString(result));
                }
                System.out.println(result);
                return acquire;
            }
        }
        return Boolean.TRUE;
    }
}
