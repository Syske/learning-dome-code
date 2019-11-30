<%--
  Created by IntelliJ IDEA.
  User: sysker
  Date: 2019-11-30
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
<h1>登陆失败！</h1>
<p>请参照如下代码</p>
<pre>
     if ("admin".equals(username)) {
            password = "admin";
        } else if ("user".equals(username)) {
            password = "user";
        } else {
            password = "123456";
        }

        if ("unkonw".equals(username)) {
            throw new UnknownAccountException("用户不存在！");
        }

        if ("locked".equals(username)) {
            throw new LockedAccountException("用户被锁定！");
        }
</pre>
</body>
</html>
