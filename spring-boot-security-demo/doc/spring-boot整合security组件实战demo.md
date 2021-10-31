# spring-boot整合security组件实战demo

### 前言

最近几天，我们一直在分享`security`相关的知识点，包括基本配置、鉴权配置、用户数据配置、自定义登录配置等，同时我们也简单演示了一些`security`具体使用的基本效果，介绍了过程中可能存在的错误和异常，但是由于是分三次分享的，内容相关而言过于零散，而且初期分享的内容，由于积累不够，内容也过于浅显，所以我今天想着做一次完整的`demo`，将之前的知识串起来。好了，话不多说，下面我们直接开始。

### 实战demo

开始之前，我们先看下项目结构：

![](https://gitee.com/sysker/picBed/raw/master/20210723075156.png)

#### 创建项目

首先我们创建一个`spring-boot`项目，引入`security`的`starter`依赖。这里就简单提一下，如果感觉有点懵的小伙伴可以翻下最近这几天我们分享的内容。

```xml
 <!-- security核心依赖 -->
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<!-- 页面解析模板 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<!-- fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.61</version>
</dependency>
<!-- guava工具包 -->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>30.1.1-jre</version>
</dependency>
```

除了前两个，第一个是`security`核心依赖，第二个是页面模板引擎，如果没有页面可以不要，后面两个可选，都是属于工具包。

#### 配置security

配置`security`的组件，第二个`configure`方法昨天我们详细介绍过了，这里就不再赘述了，这里着重说下第一个`configure`方法，这个方法的作用是设定登录认知处理器。

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private SyskeAuthenticationFailureHandler handler;

    @Autowired
    private SyskeAuthenticationProvider syskeAuthenticationProvider;

    @Autowired
    private SyskeAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(syskeAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 限定 ” /user/welcome ”请求赋予角色 ROLE_USER 或者 ROLE_ADMIN
                .antMatchers("/welcome").hasAnyRole("USER", "ADMIN", "TEST")
            // 限定 ” /admin/ ”下所有请求权限赋予角色 ROLE_ADMIN
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/test/**").hasAnyAuthority("ROLE_TEST", "ROLE_ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
            // 其他路径允许签名后访问
            .anyRequest().permitAll()
            // 对于没有配置权限的其他请求允许匿名访问
            .and().anonymous()
            // 使用spring security 默认的登录页面
            .and().formLogin().loginPage("/userLogin").loginProcessingUrl("/loginService")
             .failureForwardUrl("/fail")
             .successForwardUrl("/user/welcome")
            .failureHandler(handler)
               .defaultSuccessUrl("/welcome")
                .successHandler(successHandler)
                .and().rememberMe()
            .tokenValiditySeconds(30).key("remember-me")
            // 启动 HTTP 基础验证
            .and().httpBasic()
        .and().csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
```

这里我们还配置了密码加密器，后面登录认证的时候要用，下面我们看下如何定义登录认知处理器。



#### 登录认证处理器

自定义认证处理器只需要继承`AuthenticationProvider`接口，并实现它的两个方法即可。第一个方法最核心，我们可以在方法内部自定义我们自己的的用户登录认证处理过程。

```java
public class SyskeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = readerRepository.loadUserByUsername(authentication.getName());
        if (Objects.isNull(userDetails)) {
            authentication.setAuthenticated(false);
           throw new SyskeAuthenticationException("用户不存在");
        }
        String credentials = (String)authentication.getCredentials();
        String encodePassword = passwordEncoder.encode(credentials);
        if (passwordEncoder.matches(credentials, encodePassword)) {
            return new UsernamePasswordAuthenticationToken(userDetails, encodePassword, userDetails.getAuthorities());
        } else {
            authentication.setAuthenticated(false);
            throw new SyskeAuthenticationException("用户名或密码错误");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
```

实际测试过程中我发现，如果指定了登录认证管理器，登录的时候虽然调用的时登录接口，但是并不会进入接口内部，而是由认证处理器处理登录请求，登录成功后会调用登录成功处理器，登录失败会调用登录失败处理器。

但是需要注意的是，`security`的登录处理接口必须指定，而且指定的接口地址必须存在，哪怕是个空接口也可以，但必须得有：
![](https://gitee.com/sysker/picBed/raw/master/20210723082513.png)

第二个方法一般直接返回`true`即可，本次示例下，如果返回`false`，登录的时候前端会有异常提示：

![](https://gitee.com/sysker/picBed/raw/master/20210723080223.png)

当这个方法的返回值为`true`时，当我们的`AuthenticationProvider`提供的处理机制不够全面的时候， `security`会根据我们的配置，为我们提供更合理的`AuthenticationProvider`，确保我们的处理结果正常。

#### 登录成功处理器

继承`AuthenticationSuccessHandler`接口，实现`onAuthenticationSuccess`方法，我这里直接处理成`json`返回了

```java
@Component
public class SyskeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("message", "登录成功");
        result.put("success", Boolean.TRUE);
        result.put("code", 2000);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
```

#### 登录失败处理器

和成功处理器类似，这里是继承`AuthenticationFailureHandler`，实现`onAuthenticationFailure`方法即可，这里也是处理成`json`

```Java
@Component
public class SyskeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("message", e.getMessage());
        result.put("success", Boolean.FALSE);
        result.put("code", 1000);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
```

#### 用户信息服务

在处理器中，我们要查询用户信息，这里我通过伪代码实现了类似的逻辑，如果引入数据库的话，也很简单，只需要创建用户角色表、角色资源表、资源权限表以及资源表，这一块我们后期可以单独出一期`demo`。

```java
@Service
public class ReaderRepository implements UserDetailsService {

    private static Map<String, UserDetails> userDetailsMap = Maps.newHashMap();

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            userDetailsMap.put("admin", new UserInfo("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
            userDetailsMap.put("user", new UserInfo("user", passwordEncoder.encode("123456"), "ROLE_USER"));
            userDetailsMap.put("test", new UserInfo("test", passwordEncoder.encode("test"), "ROLE_TEST"));
        if (userDetailsMap.containsKey(username)) {
            return userDetailsMap.get(username);
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    public static class UserInfo implements UserDetails {
        private String username;
        private String password;
        private String[] authorities;

        public UserInfo() {
        }

        public UserInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public UserInfo(String username, String password, String... authorities) {
            this.username = username;
            this.password = password;
            this.authorities = authorities;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorityList = Lists.newArrayList();
            for (String authority : authorities) {
                authorityList.add(new SimpleGrantedAuthority(authority));
            }
            return authorityList;
        }
// getter setter方法省略
    }
}
```

#### 集中异常处理

这里我做了一个简单的全局集中异常处理机制，对于非网页资源的错误，这里会处理成`json`然后返回，对于页面错误，会根据具体的错误码返回对应的页面，比如`403`、`404`、`500`等。

```java
@RestControllerAdvice
@RequestMapping(value = "error")
public class SyskeExceptionHandler extends BasicErrorController {

    public SyskeExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }

    @Override
    @RequestMapping(produces = {MediaType.ALL_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> result = Maps.newHashMap();
        HttpStatus status = this.getStatus(request);
        result.put("code", -1);
        result.put("message", "未知错误");
        result.put("success", false);
        result.put("status", status.value());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);
        response.setStatus(status.value());
        return new ModelAndView(String.valueOf(status.value()));
    }
}
```

对集中异常感兴趣的小伙伴可以看下我们之前发过的一篇推文，那里面说的很详细了。



#### 页面资源配置类

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/userLogin").setViewName("login");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/user/hello").setViewName("user/hello");
        registry.addViewController("/admin/hello").setViewName("admin/hello");
        registry.addViewController("/test/hello").setViewName("test/hello");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/403").setViewName("403");
    }
}
```



#### 相关页面

下面就是我们的相关页面了，目前页面都没有加样式，也写的很简单：

- 登录页：

    ```html
    <body>
        用户名：<input name="username" id="username" type="text"><br>
        密码：<input name="password" id = "password" type="password"><br>
        <button id="login-btn">登录</button>
        <input name="_csrf" type="hidden">

    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="application/javascript">
        $(function () {
           $("#login-btn").click(function () {
               $.ajax({
                   url: "/loginService",
                   method: "post",
                   dataType:"json",
                   data: {"username": $("#username").val() , "password": $("#password").val()},
                   success: function (rep) {
                       console.log(rep)
                       if (rep.success) {
                           console.log("登录成功");
                           window.location.href = "/welcome";
                       } else {
                           alert(rep.message)
                       }
                   }
               })
           })
        })
    </script>
    </body>
    ```

- 欢迎页

    ```html
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>欢迎页</title>
    </head>
    <body>
    你好
    </body>
    </html>
    ```

- 首页

  ```html
  <!DOCTYPE html>
  <html lang="en">
  <head>
      <meta charset="UTF-8">
      <title>我是首页</title>
  </head>
  <body>
  我是首页，跳转至：<a href="/welcome">欢迎页</a>
  </body>
  </html>
  ```


- 403页面（无权访问跳转的页面）

  ```xml
  <!DOCTYPE html>
  <html lang="en">
  <head>
      <meta charset="UTF-8">
      <title>禁止访问</title>
  </head>
  <body>
  当前用户没有权限访问相关资源
  
  <SCRIPT type="text/javascript">
      var maxtime =  10; // 按秒计算，自己调整!
      function CountDown() {
          if (maxtime >= 0) {
              seconds = Math.floor(maxtime % 60);
              msg = seconds + "秒后跳转至首页";
              document.getElementById("timer").innerHTML = msg;
              --maxtime;
          } else{
              clearInterval(timer);
              window.location.href = "/index"
          }
      }
      timer = setInterval("CountDown()", 1000);
  </SCRIPT>
  <div id="timer" style="color:red"></div>
  <div id="warring" style="color:red"></div>
  </body>
  </html>
  ```

  页面就不一一展示了，感兴趣的小伙伴可以去看下完整源码，文末有源码地址。

### 总结

今天的内容虽然多，但也只是这几内容的汇总和整合，前几天分享的都是比较细节和零碎的东西，今天主要是通过一个`demo`让各位小伙伴更直观地认识`security`，如果可能的话，我希望最近的内容能够在实际开发中帮到你。

另外，想吐槽的是，目前市面上很多书对于很多知识点其实真的是一笔带过，说的既不具体，也不够深入。说他浅显把对小白不够友好，该讲的内容不讲，看了半天感觉和没看一样；说他讲的深刻吧，讲解的内容又不够深入，该探讨的问题也不探讨，看完以后感觉他讲的你基本上都知道，还浪费了实际。

所以，我还是觉得，实用性知识要多动手，多实践，多总结，多思考，凡是想通过看视频看书有显著提升的，我建议你洗洗睡吧。纯粹吐槽，请不要对号入座！

最后，附上我们今天内容的源码地址：

```
https://github.com/Syske/learning-dome-code/tree/dev/spring-boot-security-demo
```

