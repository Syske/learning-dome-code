# spring-boot启用security组件 · 下

### 前言

经过昨天和前天的分享，我们对`security`有了一些基本的认识，同时也可以基于它做一些简单的应用，但是学习知识不能一知半解，这样即不方便应用，也不方便我们对知识的整体理解和掌握，所以为了能够对`security`有更深入的了解和认识，我们今天还是继续了解`sercurity`组件。

今天的内容主要是对`HttpSecurity`的配置做一些补充说明，为我们明天做完整`demo`做准备，好了，下面我们直接开始吧。

### 自定义登录

今天我们主要分享自定义登录相关内容。

#### 修改配置

首先我们要修改配置类，我们先说`configure(HttpSecurity http)`方法：

![](https://gitee.com/sysker/picBed/raw/master/images/20210722154951.png)

下面我们分块讲解，先说资源权限配置。

##### 资源权限配置

这一块昨天其实也说过了，但是涉及的规则比较多，所以还是要多试多实践

```java
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
```

先解释下上面的权限配置，第一行配置的`/welcome`，仅允许角色为`USER`、`ADMIN`、`TEST`的用户访问，这个是但个资源设定访问权限；

第二行指定的是`/admin/**`，仅允许拥有管理权限的用户访问，因为是通配符，所以`admin/`路径下的所有资源都可以访问；

第三行和第四行的配置和第二行差不多，不过访问权限指定的是多权限，只要具备其中一个权限即可访问；

第五行和第六行是设定其他未设定权限的资源访问权限，一般只指定一种即可。关于两者的区别，网上有种说法是，匿名资源只允许不登录的用户访问，已登录的用户无法访问，而`permitAll`没有这种限制，但是我实际测试发现，两者没有本质区别，等后续深入了解之后再来探讨吧，有小伙伴清楚的，也可以留言，让我们都知道下。

##### 登录页面设置

这里主要是设置登录相关的处理接口和页面，通过这里的配置，我们可以用自己的接口替换`security`组件的默认配置，实现更灵活的鉴权设置。

```
// 使用spring security 默认的登录页面
			// 设置登录相关页面
            .and().formLogin()
            // 登录页
            .loginPage("/userLogin")
            // 登录处理接口
            .loginProcessingUrl("/loginService")
             // 登录成跳转理页面
            .successForwardUrl("/user/welcome")
             // 登录成功处理器
            .successHandler(successHandler)
            // 默认成功页面
            .defaultSuccessUrl("/welcome")
            // 失败跳转页面
            .failureForwardUrl("/fail")           
            // 失败处理器
            .failureHandler(handler)
```

这里，我们简单解释下，第三行设置的是登录页面，也就是我们前天分享内容中的登录页：

![](https://gitee.com/sysker/picBed/raw/master/20210720085510.png)

第四行设置的是登录处理接口，这里必须是`post`接口，如果是`get`接口登录的时候，返回是有问题的：

![](https://gitee.com/sysker/picBed/raw/master/images/20210722183413.png)

经过我的实测，发现前端请求的时候必须是`post`（不论后端是`post`还是`get`，这也说明在`spring-boot`中，`get`接口是支持`post`方式请求，但是反过来不行），如果是`get`请求，就是上面的错误。

第五行设置的是登录成功后的页面；

第六行设置的是登录成功处理器，这个和第五行的页面是冲突的，但是后面的配置会覆盖前面的设置（谁在后面就会覆盖前面的设置），这里我们可以根据自己的需要设定返回值的类型，比如`json`

第七行设置的是默认登录成功页，和第五行设置的一样，这个设置也会和处理器冲突，区别是第五行的设置会跳转到新的页面，地址栏会发生改变，第七行的设置会返回一个新的页面替换当前页面的内容，但是地址栏不会发生变化，就像`forward`和`sendRedirect`的区别。

第八行设置失败跳转页面，这里和第五行类似，不过是跳转失败页；

最后一个设置的是失败处理器，这里我们可以设定的自己的处理器，让他返回`json`

##### 其他配置

这里主要讲两个设置，一个就是用户的`remeberMe`设置，和跨域攻击验证设置。

```
 // 记住我，token过期时间，用户cookie名
            .and().rememberMe().tokenValiditySeconds(30).key("remember-me")
            // 启动 HTTP 基础验证
            .and().httpBasic().and()
            // 禁用跨域csrf验证
            .csrf().disable();
```

`remeberMe`设置的是用户的`cookie`信息，`tokenValiditySeconds`设置的`token`过期时间，`key`设置的是`cookie`的名字。但是这里设置了，好像没啥用，应该是我打开方式不对，后面再研究下。

最后一行设置的是禁用`csrf`校验，如果你的接口有跨域登录的错误的话，解决方法就是禁用跨域校验。

### 总结

今天主要是一些补充内容，相比昨天会更细致，内容也更丰富，但是还有一些内容还没分享，比如登录成功处理器、登录失败处理器、登录认证处理器等，这些内容我打算明天放到具体实例中分享，所以明天的内容也很多，明天我会分享一个`security`的实例，实现完整的登录鉴权等操作，让大家可以更直观地认识`security`，好了，今天就先到这里吧！

