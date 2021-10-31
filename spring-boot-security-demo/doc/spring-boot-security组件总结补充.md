# spring-boot-security组件总结补充

### 前言

`spring-boot-security`相关知识暂时告一段落，今天我们抽点时间做一次总结，也算是对最近几天知识的回顾。

### Security

#### 补充内容

我们先说补充内容，在我们之前分享`security`组件有两个知识点被我遗漏了，然后今天总结的时候才发现，所以今天我们在这里补充下。这两个知识点都是和配置类相关的，一个是登出的相关配置，一个是`token`的相关配置。

##### 退出登录设置

先看登出配置，登出配置就是设置用户退出登录相关页面、接口地址、处理器等，和登录设置类似：

```java
// 获取登出设置对象 
.and().logout()
    // 设置登出地址
     .logoutUrl("/logout")
    // 设置登出成功后跳转地址
     .logoutSuccessUrl("/logoutPage")
    // 设置退出成功后处理器
     .logoutSuccessHandler(syskeLogoutSuccessHandler)
```

这里简单解释下：

- `logoutUrl`设置的是退出登录的触发地址，这个地址可以随便指定，哪怕资源不存在也不影响；
- `logoutSuccessUrl`设置的是退出登录成功后跳转的页面，如果你需要退出登录成功后返回一个页面，那推荐你用这种方式，这时候返回的就是你指定的页面
- `logoutSuccessHandler`设置退出登录处理器，如果你需要返回`json`之类的数据，那可以用这种方式

但是`logoutSuccessUrl`和`logoutSuccessHandler`只能设置一个，而且后面的会把前面的设置的覆盖掉，所以你需要根据自己的需要选择。

下面是我写的退出登录成功处理器的实现：

```java
@Component
public class SyskeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 登出成功后的相关操作
        System.out.println("退出登录成功");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("message", "退出登录成功");
        result.put("success", Boolean.TRUE);
        result.put("code", 2000);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
```



**说明：**昨天我们说登录接口要放一个空接口，但是搞清楚`logoutUrl`的地址之后，我想`loginProcessingUrl`应该也是这也，所以又去试了下，发现确实一样，登录接口是可以随意指定的，看来昨天可能是配置的问题。

##### token相关配置

`token`的相关设置今天又实际搞了下，暂时还没理清楚实际应用场景，而且设置的参数也没有任何效果，所以今天就先不分享，后续搞清楚了再来分享。

#### 内容总结

和上次线程池总结一样，我们先贴一张脑图（公众号回复【`spring-boot-security`】获取脑图源文件），今天的总结内容有就是围绕脑图展开。

![](https://gitee.com/sysker/picBed/raw/master/images/20210724095345.png)

`security`组件的核心内容基本上就是我们这几天分享的知识点，主要以`security`配置为主，包括配置组件的自定义、常用的配置项等，另外`security`还对第三方安全框架做了支持，比如`oauth2`、`openid`、`saml2`，好像目前就支持这三种，不过这三种已经算是比较流行的安全框架了。

![](https://gitee.com/sysker/picBed/raw/master/images/20210724115421.png)

以下就是我们`security`相关的知识点，获取脑图公众号回复【`spring-boot-security`】获取脑图源文件即可。

##### 配置类

- 常用配置方法
    - `configure(AuthenticationManagerBuilder auth)`：配置登录认证处理
    - `configure(HttpSecurity http)`：配置资源权限、登录页面等
    - `configure(WebSecurity web)`：WebSecurityConfigurerAdapter

##### 资源鉴权设置

- `authorizeRequests`：获取资源权限设置对象
- `antMatchers`：设定访问资源表达式
- `hasAnyRole`：配置资源的访问角色
- `hasAnyAuthority`：配置资源的访问权限
- `anyRequest`：除配置权限资源外的其他资源
- `permitAll`：设定指定资源的访问权限为无条件访问
- `anonymous`：设定允许匿名访问

##### 登录配置

- `formLogin`：获取登录页设置对象
- `loginPage`：设置登录页
- `loginProcessingUrl`：设置登录处理接口
- 登录成功
  - `successHandler`：设置登录成功处理器
  - `successForwardUrl`：设置登录成功跳转页面
  - `defaultSuccessUrl`：设置登录成功默认页面
- 登录失败
  - `failureForwardUrl`：设置登录失败跳转页面
  - `failureHandler`：设置登录失败处理器

##### 登出配置

- `logout`：获取登出设置对象
- `logoutUrl`：设置登出接口地址
- `logoutSuccessHandler`：设置登出成功处理器
- `logoutRequestMatcher`：设置登出匹配地址（访问设定的地址，就会触犯登出操作）
- `addLogoutHandler`：添加登出处理器
- `defaultLogoutSuccessHandlerFor`：设定登出成功处理器、登出匹配地址
- `deleteCookies`：设定是否删除`cookie`

##### `token`相关配置

- `tokenValiditySeconds`：设置`token`过期时间
- `tokenRepository`：设置`token`处理器（生成、删除、更新）

##### 其他配置

- `userDetailsService`：设定用户数据获取服务
- `httpBasic`：启用http基本校验
- `csrf`：设置跨越访问校验

##### 组件依赖

- `spring-boot-starter-security`

##### 扩展

- `oauth2`
- `openid`
- `saml2`

##### 组件

- 登录认证处理器：`AuthenticationProvider`
- 登录结果处理器：
  - `AuthenticationFailureHandler`
  - `AuthenticationSuccessHandler`
- 登出处理器
  - `LogoutSuccessHandler`
  - `LogoutHandler`
- `token`组件：`PersistentTokenRepository`
- 用户信息组件：`UserDetailsService`	
- 密码加密器：`PasswordEncoder`

#### 前期知识点回顾



### 总结

截止到今天，`security`相关的基础知识我们就算讲完了，虽然好多知识也没讲（第三方安全框架整合、`token`等），但是也算是把`security`的一些基础知识讲的差不多了，而且在一些细节问题的探讨和研究上（比如登录、登出等配置），我应该讲的算比较细了，当然从我的角度来说，经过这几天的梳理和摸索，我基本上把`securtiy`的整个使用流程搞清楚了（说到这里，我发现我忘记画流程图了），至少在`security`使用方面不会有太大问题，总之我是有收获的。

最后我希望这几天的内容能够真正帮助到大家，周末愉快呀！
