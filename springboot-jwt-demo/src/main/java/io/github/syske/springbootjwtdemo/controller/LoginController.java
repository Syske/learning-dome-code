package io.github.syske.springbootjwtdemo.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.syske.springbootjwtdemo.dao.entity.ResponseBean;
import io.github.syske.springbootjwtdemo.dao.entity.Result;
import io.github.syske.springbootjwtdemo.dao.entity.User;
import io.github.syske.springbootjwtdemo.sevice.JwtService;
import io.github.syske.springbootjwtdemo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-13 18:39
 */
//@Controller
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtService jwtService;

    /**
     * 登录
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public Result<JSONObject> toLogin(@RequestBody User loginUser) throws Exception {
        Result<JSONObject> result = new Result<>();
        // 生成token
        String token = jwtService.login(loginUser);
        JSONObject obj = new JSONObject();
        obj.put("accessToken", token);
        obj.put("userInfo", loginUser);
        result.setResult(obj);
        result.setStatus(200);
        result.success("登录成功");

        return result;
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized", null);
    }


}
