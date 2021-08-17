# spring-boot启用security组件

### 前言

从今天开始，我打算系统地回顾下`spring-boot`的相关知识，但是在系统学习之前，我想先把之前没有了解过的知识过一遍，之后再系统地总结。今天我们就先来看下`spring-boot`的官方组件`secutity`，下面我们就直接开始吧！

### Security组件

这个组件是`spring-boot`的基础组件之一，主要用于`spring-boot`项目的权限控制，我们今天主要是看下它的基础配置和一些简单应用。

开始之前，我们要先创建一个`spring-boot`项目。

#### 创建项目

项目的依赖很简单，最核心的依赖就两个，一个就是`spring-boot-starter`，这个依赖是`spring-boot`最基础的依赖，没有之一，但凡你创建`spring-boot`项目都会有这个依赖；

另外一个核心依赖就是我们今天的主角——`spring-boot-starter-security`；

除了两个核心依赖，我还引入了`guava`的依赖，这是一个谷歌出品的工具类包，其中有很多特别方便的工具类，比如容器操作。

```xml
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>30.1.1-jre</version>
    <!-- or, for Android: -->
    <!--<version>30.1.1-android</version>-->
</dependency>
```

加入以上配置之后，我们不需要做任何配置，`security`其实已经被我们集成到`spring-boot`中了，不信你启动下看看：

![](https://gitee.com/sysker/picBed/raw/master/images/20210720125636.png)

如果你的控制台也有如上信息，说明你的`spring-boot`项目已经集成了`security`，最上面打印的就是我们的登录密码，用户名默认情况下是`user`，下面我们简单测试下。

#### 测试

首先，我们要写一个`controller`，没有`controller`的话，确实不好测试。`controller`中，我就写了一个简单的`testSecurity`方法，方法内部打印`helle`信息。

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

`controller`创建完成后，我们再次启动项目，然后访问`http://localhost:8989/test/security?name=syske`

![](https://gitee.com/sysker/picBed/raw/master/images/20210720130522.png)

正常情况下，会跳转到登录页面，这就说明`security`组件已经起作用了。

![](https://gitee.com/sysker/picBed/raw/master/20210720085510.png)

这里登录的用户名是`user`，密码就是控制台打印出来的密码，输入用户名和密码，然后登录，这时候接口就正常返回了：

![](https://gitee.com/sysker/picBed/raw/master/images/20210720130430.png)

登录成功后，你后面再访问其他接口也是不需要再次登陆的。而且我试过，就算你关闭浏览器，再次打开，也是不需要登陆的，但是你重启`spring-boot`服务之后，就需要重新登录了，目前还不清楚具体的鉴权原理，从浏览器请求情况来看，不是`token`，后面研究下。

`security`虽然起作用了，但是我们在实际应用的时候，不可能都用`user`和默认密码登录吧，毕竟不方便，也不合理，所以下面我们就来看戏如何来配置`security`，让它满足我们更复杂的应用场景。

#### 配置security

首先，我们要创建一个配置类，这个配置类要继承`WebSecurityConfigurerAdapter`，并重写其中的`configure`方法，这个类有多个`configure`方法，这里我们重写的是参数为`AuthenticationManagerBuilder`，其他配置类，我们后面继续讲。

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private ReaderRepository readerRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            System.out.println(username);
            return readerRepository.loadUserByUsername(username);
                    }).passwordEncoder(new BCryptPasswordEncoder());
    }
}
```

这个方法其实就是用户登录时的鉴权方法，我们通过重写这个方法，可以实现我们自己的鉴权操作。我这里现在都是写死的，只要你登录的用户是`admin`，就可以鉴权通过，密码我也是写死的。

`userDetailsService`方法的作用是配置用户信息查询服务，我们需要继承`UserDetailsService`接口，并实现其中的`loadUserByUsername`方法，这个方法会返回 `UserDetails`，也就是用户基本信息，主要是密码和用户名，还有过期时间这些：

![](https://gitee.com/sysker/picBed/raw/master/images/20210720132411.png)

下面是我写的一个构建`UserDetails`的服务，后期的话，可以根据自己的需要整合数据库。

```java
@Service
public class ReaderRepository implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return new UserInfo("admin", new BCryptPasswordEncoder()
                    .encode("admin"));
        } else {
            return null;
        }
    }

    public static class UserInfo implements UserDetails {
        private String username;
        private String password;

        public UserInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Arrays.asList(new SimpleGrantedAuthority("READER"));
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
```

然后，重启我们的`spring-boot`项目，这时候我们就不能用默认的用户名和密码登录了，控制台也不会有密码打印了，而是用我们设置的用户名和密码登录。

这里需要注意的是，`passwordEncoder(new BCryptPasswordEncoder())`的作用是指定密码的加密器，这里的加密器必须和你保存密码时用的加密器一致，否则密码校验无法通过。

如果你不指定密码加密器，鉴权的时候也是会报错的：

![](https://gitee.com/sysker/picBed/raw/master/images/20210720133516.png)

另外，还有一点比较重要，就是我们在登录的时候，其实就是调用`userDetailsService`的`loadUserByUsername`获取用户信息，然后进行鉴权操作，所以如果要修改`security`组件的相关配置，就必须实现`loadUserByUsername`方法，这样才能确保你的用户数据是可控的。

### 总结

今天我们分享了`spring-boot`启用`security`组件的相关知识点，整个过程还是比较简单，但是由于时间的关系，好些点我们没有展开来讲，也讲的不够细，不过也没有关系，因为最近一段时间我们都打算分享`spring-boot`相关知识，所以明天我们会继续分享.

好了，今天的内容就到这里吧，我们明天继续。