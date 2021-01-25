package io.github.syske.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.syske.aop.util.HttpContextUtils;
import io.github.syske.aop.util.IPUtils;
import io.github.syske.aop.util.UUIDUtil;
import io.github.syske.entity.UpmsLog;
import io.github.syske.service.UpmsLogService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: publicCoreServer
 * @description: aop 打印 接收参数日志
 * @author: liu yan
 * @create: 2019-11-21 09:15
 */


@Aspect
@Component//定义一个切面
public class LogRecordAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);

    @Autowired
    private UpmsLogService upmsLogService;


    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void logPointCut() {

    }


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, beginTime, time, result);

        return result;
    }

    /**
     * 日志记录
     *
     * @param joinPoint
     * @param beginTime 开始时间
     * @param spendTime  用时
     * @param result  返回结果
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint,long beginTime, long spendTime, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        UpmsLog upmsLog = new UpmsLog();
        upmsLog.setLogId(UUIDUtil.getUUID());
        upmsLog.setAppId("pc");

        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if(annotation != null){
            //注解上的描述
            upmsLog.setDescription(annotation.value());
        }

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        upmsLog.setParameter(params);

        if(result instanceof String) {
            upmsLog.setResult(result.toString());
        } else {
            // 返回结果
            String resultJson = JSON.toJSONString(result);
            JSONObject jsonObject = JSON.parseObject(resultJson);

            if ("true".equals(jsonObject.getString("success")) && resultJson.length() > 500) {
                upmsLog.setResult("调用成功，返回结果过长被省略");
            } else {
                upmsLog.setResult(resultJson);
            }
        }



        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        upmsLog.setIp(IPUtils.getIpAddr(request));
        // 请求路径
        String requestUrl = request.getRequestURL().toString();
        upmsLog.setUrl(requestUrl);
        String requestURI = request.getRequestURI();
        upmsLog.setUri(requestURI);

        // 请求头
        String agentString = request.getHeader("User-Agent");
        upmsLog.setUserAgent(agentString);

        // 请求方式
        String authType = request.getMethod();
        upmsLog.setMethod(authType);

        //用户名，如果用户登陆，则获取用户名，否则不记录
      /*  Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object userId = subject.getPrincipal();
            upmsLog.setUsername(userId.toString());
            upmsLog.setUserId(userId.toString());
        }*/

        String path = request.getContextPath();
        String baseURL =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        upmsLog.setBasePath(baseURL);

        // 开始时间
        upmsLog.setStartTime(beginTime);
        // 用时
        upmsLog.setSpendTime(spendTime);
        upmsLog.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //保存系统日志
        logger.info("日志信息："+ upmsLog);
        upmsLogService.insert(upmsLog);
    }


}