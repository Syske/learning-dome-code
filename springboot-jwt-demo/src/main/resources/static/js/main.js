function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

function checkLogin() {
    var token = localStorage.getItem("token");
    if (token == null || token.length == 0) {
        goLogin();
    } else {
        //有token，检查token是否还有效
        $.ajax({
            type: 'post',
            url: "/sso/checkJwt",
            beforeSend: function (request) {      //使用beforeSend
                request.setRequestHeader("Access-Token", localStorage.getItem("token"));
                request.setRequestHeader("Content-Type", "application/json");
            },
            data: {"token": token},
            success: function (res) {
                console.log(res);
                if (res.data === true) {
                    alert('已登录，跳转到回调页面');
                    var redirect = getUrlParam("redirect");
                    if(redirect != null)
                        window.location.href = redirect;
                    else
                        window.location.href = "/sso/index";
                } else {
                    goLogin();
                }
            }
        });
    }
}

function goLogin() {
    alert("无认证信息，即将跳转到登录页面");
    var redirect = getUrlParam("redirect");
    if(redirect != null)
        window.location.href = encodeURI("/sso/userLogin?redirect=" + redirect);
    else
        window.location.href = encodeURI("/sso/userLogin?redirect=" + "/sso/index");

}