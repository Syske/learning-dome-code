package io.github.syske.springbootjwtdemo.shiro.filter;

import io.github.syske.springbootjwtdemo.exception.AuthorizationException;
import io.github.syske.springbootjwtdemo.sevice.JwtService;
import io.github.syske.springbootjwtdemo.shiro.JwtToken;
import io.github.syske.springbootjwtdemo.util.CommonConstant;
import io.github.syske.springbootjwtdemo.util.SpringContextUtil;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: springboot-jwt-demo
 * @description: jwt过滤器
 * @author: syske
 * @create: 2020-03-13 18:32
 */

public class JwtFilter extends BasicHttpAuthenticationFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 执行登录认证(判断请求头是否带上token)
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        logger.info("JwtFilter-->>>isAccessAllowed-Method:init()");
        //如果请求头不存在token,则可能是执行登陆操作或是游客状态访问,直接返回true
        if (isLoginAttempt(request, response)) {
            return true;
        }
        //如果存在,则进入executeLogin方法执行登入,检查token 是否正确
        executeLogin(request, response);
        return true;
    }

    /**
     * 判断用户是否是登入,检测headers里是否包含token字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        logger.info("JwtFilter-->>>isLoginAttempt-Method:init()");
        HttpServletRequest req = (HttpServletRequest) request;
        if (antPathMatcher.match("/userLogin", req.getRequestURI())) {
            return true;
        }
        String token = req.getHeader(CommonConstant.ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        JwtService jwtService = (JwtService) SpringContextUtil.getBean("jwtService");
        Boolean isPass = (jwtService.checkJwt(token).getCode() == 1);
        if (!isPass) {
            return false;
        }
        logger.info("JwtFilter-->>>isLoginAttempt-Method:返回true");
        return true;
    }

    /**
     * 重写AuthenticatingFilter的executeLogin方法丶执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        logger.info("JwtFilter-->>>executeLogin-Method:init()");
        String token = getTokenFromRequest((HttpServletRequest) request);
        if (StringUtils.isEmpty(token)) {
            throw new AuthorizationException("未找到用户token令牌信息，请登陆");
        }
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入,如果错误他会抛出异常并被捕获, 反之则代表登入成功,返回true
        getSubject(request, response).login(jwtToken);
        return true;
    }

    /**
     * 从request头中获取token
     *
     * @param request
     * @return
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        HttpServletRequest httpServletRequest = request;
        return httpServletRequest.getHeader(CommonConstant.ACCESS_TOKEN);
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("JwtFilter-->>>preHandle-Method:init()");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

}
