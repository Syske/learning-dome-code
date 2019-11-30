<%--
  Created by IntelliJ IDEA.
  User: sysker
  Date: 2019-12-01
  Time: 1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AoP日志记录测试</title>
    <script rel="script" src="static/js/jquery.js"></script>
</head>
<body>
<div id="response" style="border:1px solid #ccc;width: 200px;height: 100px">

</div><br>
名字：<input type="text" id="name">
<a href="javscript:void(0)" onclick="sendMsg()">点击测试</a>
<script type="text/javascript">
    function sendMsg() {
        $.ajax({
            url:"log/hello",
            data: {"name":$("#name").val()},
            type: "get",
            success:function (response) {
                $("#response").append(response+"<br>");
            }
        });

    }
</script>
</body>
</html>
