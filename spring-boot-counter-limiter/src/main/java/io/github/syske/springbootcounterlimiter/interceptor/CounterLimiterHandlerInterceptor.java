/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootcounterlimiter.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.syske.springbootcounterlimiter.annotation.CounterLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-31 21:52
 */
@Component
public class CounterLimiterHandlerInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(CounterLimiterHandlerInterceptor.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(CounterLimit.class)) {
                CounterLimit annotation = handlerMethod.getMethod().getAnnotation(CounterLimit.class);
                JSONObject result = new JSONObject();
                String token = request.getParameter(annotation.name());
                response.setContentType("text/json;charset=utf-8");
                result.put("timestamp", System.currentTimeMillis());
                BoundValueOperations<String, Integer> boundGeoOperations = redisTemplate.boundValueOps(token);
                if (StringUtils.isEmpty(token)) {
                    result.put("result", "token is invalid");
                    response.getWriter().print(result);
                } else if (checkLimiter(token, annotation)) {
                    result.put("result", "请求成功");
                    Long expire = boundGeoOperations.getExpire();
                    logger.info("result：{}, expire: {}",  result, expire);
                    return true;
                } else {
                    result.put("result", "达到访问次数限制，禁止访问");
                    Long expire = boundGeoOperations.getExpire();
                    logger.info("result：{}, expire: {}",  result, expire);
                    response.getWriter().print(result);
                }
                return false;
            }
        }
        return true;
    }

    private Boolean checkLimiter(String token, CounterLimit annotation) {
        BoundValueOperations<String, Integer> boundGeoOperations = redisTemplate.boundValueOps(token);
        Integer count = boundGeoOperations.get();
        if (Objects.isNull(count)) {
            redisTemplate.boundValueOps(token).set(1, annotation.timeout(), annotation.timeUnit());
        } else if (count >= annotation.limitTimes()) {
            return Boolean.FALSE;
        } else {
            redisTemplate.boundValueOps(token).set(count + 1, boundGeoOperations.getExpire(), annotation.timeUnit());
        }
        return Boolean.TRUE;
    }
}
