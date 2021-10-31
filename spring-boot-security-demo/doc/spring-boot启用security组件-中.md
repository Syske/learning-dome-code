# spring-boot启用security组件 · 中

### 前言

昨天我们分享了`spring-boot`启用`security`组件的一些基础知识，演示了`security`的基本配置和简单用法，虽然也可以应用于实际开发，但还是过于简单，并不能真正发挥`sercurity`的作用，所以今天我们还要继续深挖`security`的其他配置和用法。目前，我计划花三天时间分享`security`相关知识点，不过具体还是要看实际情况。

好了，话不多说，我们直接开整。

### security

#### 用户名及密码配置补充

开始之前，我们先补充一个`security`组件密码和用户名配置的知识点，昨天我们分享了通过配置类整合我们自己的用户数据，在翻看`spring boot`相关书籍的时候，我发现它还有另外一种方式配置用户名和密码——配置文件，配置方式也很简单，就是在我们的`application.properties`文件中添加如下配置：

```properties
spring.security.user.name=myuser
spring.security.user.password=l23456
```

但是，需要把我们昨天加的配置类和`service`先注释掉，否则会有冲突。在我实际测试过程中，我发现只要实现了`UserDetailsService`类，加上`@service`注解（不需要配置类），其实已经相当于自定义了`security`组件的用户数据，只是后台会报错误：

![](https://gitee.com/sysker/picBed/raw/master/20210721082251.png)

所以我们还是需要通过配置类设定加密器，关于用户名和密码配置，还有另外一种方式，也是基于配置类的：

```java
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("syske").password("123456");
        }
```

这种方式和配置文件的方式差不多一样，但是配置文件是支持多用户配置的：

```java
auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("syske").password("123456").and()
                .withUser("admin").password("admin");
```

#### 配置登录页面和资源权限控制

下面我们分享`security`的另一个配置组件，这个组件的作用主要是配置页面和用户可访问的资源，我们可以在这个方法下设置用户登录页。

默认配置如下：

```java
protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and()
                .formLogin().and()
                .httpBasic();
    }
```

我们可以根据自己的需要修改配置：

```java
http.authorizeRequests()
                //限定 ” /user/welcome ”请求赋予角色 ROLE_USER 或者 ROLE_ADMIN
                .antMatchers("/user/welcome").hasAnyRole("USER", "ADMIN")
                // 限定 ” /admin/ ”下所有请求权限赋予角色 ROLE_ADMIN
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                // 其他路径允许签名后访问
                .anyRequest().permitAll()
                // 对于没有配置权限的其他请求允许匿名访问
                .and().anonymous()
                // 使用spring security 默认的登录页面
                .and().formLogin()
                // 启动 HTTP 基础验证
                .and().httpBasic();
```

这里我们简单介绍下，`authorizeRequests()`方法的作用是创建权限配置对象，并把配置对象注入`spring boot`：

![](https://gitee.com/sysker/picBed/raw/master/images/20210721132103.png)

其他的配置操作都是基于这个配置对象展开的。

- `antMatchers`的作用是添加地址匹配规则，支持正则表达式；

- `hasAnyRole`一般是和`antMatchers`成对出现的，它的作用是设定`antMatchers`的访问权限，只要具有指定权限，即可访问前面配置的访问规则；
- `hasAnyAuthority`和`hasAnyRole`类似，它也和`antMatchers`成对出现，所不同的是`hasAnyAuthority`要指定的是具体的权限
- `and `方法 ，它是连接词，表示可以重新加入新的权限验证规则，就相当于把配置对象返回了
- `anyRequest`表示上面限定的所有请求，即`antMatchers("/user/welcome").hasAnyRole("USER", "ADMIN")`这样的配置
- `permitAll` 表示对请求（具体看请求类别）无条件允许访问
- `anonymous`表示对请求（具体看请求类别）允许匿名访问
- `formLogin`表示启用`security`默认登录页，我们也可以设定自己的登录页和登录成功的页面

![](https://gitee.com/sysker/picBed/raw/master/images/20210721134604.png)

- `httpBasic`表示启用浏览器的`http`基础验证

更多配置方法可以参考这张图片：

![](https://gitee.com/sysker/picBed/raw/master/images/20210721133929.png)



#### 测试

我们定义几个`controller`测试一下：

```java
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("security")
    public Object testSecurity(String name) {
        return "hello, " + name;

    }
}
```

管理`controller`

```java
@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("security")
    public Object testSecurity(String name) {
        return "hello, admin -" + name;

    }
}
```

普通用户`controller`

```java
@RestController
@RequestMapping("/user")
public class UserController {

        @GetMapping("security")
        public Object testSecurity(String name) {
            return "hello, user -" + name;

        }

    @PostMapping("welcome")
    public Object welcome(String name) {
        return "welcome, user -" + name;

    }
}
```

然后我们把获取用户的数据进行了修改，将用户进行了伪代码处理：

```java
private static Map<String, UserDetails> userDetailsMap = Maps.newHashMap();

    static {
        userDetailsMap.put("admin", new UserInfo("admin", encrypt("admin"), "ADMIN"));
        userDetailsMap.put("user", new UserInfo("user", encrypt("123456"), "USER"));
        userDetailsMap.put("test", new UserInfo("test", encrypt("test"), "TEST"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userDetailsMap.containsKey(username)) {
            return userDetailsMap.get(username);
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
private static String encrypt(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
```

需要注意的是，`.successForwardUrl("/user/welcome")`的地址只能是`post`接口，如果是`get`接口的话，登录成功后会报错：

![](https://gitee.com/sysker/picBed/raw/master/images/20210721193832.png)

### 总结

今天我们主要分享了`security`组件的另一个配置方法 —— `configure(HttpSecurity http)`，演示了一些简单的配置，由于时间的问题，能分享的内容确实也比较有限，而且`spring boot`单个组件内容又比较多，所以目前还找不到更合适的分享方式，不过各位小伙伴也不用太着急，组件完成后我会做一个系统的`demo`将所有知识点串起来。好了，今天的内容就到这里吧，今天很忙忙，所以内容更新的也有点晚了。
