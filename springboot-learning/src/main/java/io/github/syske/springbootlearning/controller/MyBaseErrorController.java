package io.github.syske.springbootlearning.controller;

import com.alibaba.fastjson.JSON;
import io.github.syske.springbootlearning.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping(value = "error")
public class MyBaseErrorController implements ErrorController {
    private static final String path_default = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;


    @RequestMapping(produces = {MediaType.ALL_VALUE})
    public void error(HttpServletRequest request,  HttpServletResponse response) {
        setJsonError(response);
    }


    @RequestMapping(
            produces = {"text/html"}
    )
    public void errorHtml(HttpServletRequest request, HttpServletResponse response) {
        setJsonError(response);
    }

    @Override
    public String getErrorPath() {
        return path_default;
    }

    private void setJsonError(HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setStatus(200);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(Result.getFailed("未知错误", null)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
