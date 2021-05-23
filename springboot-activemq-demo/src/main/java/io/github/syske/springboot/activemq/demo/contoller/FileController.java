package io.github.syske.springboot.activemq.demo.contoller;

import com.alibaba.fastjson.JSONObject;
import io.github.syske.springboot.activemq.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: springboot-activemq-demo
 * @description: 文件接口
 * @author: syske
 * @date: 2021-05-23 10:42
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/file/{user_id}/export")
    public JSONObject fileExport(@PathVariable("user_id") String userId,
                                 @RequestParam String name) {
        return fileService.export(userId, name);
    }

    @GetMapping("/file/{user_id}/download/{file_id}")
    public JSONObject download(@PathVariable("user_id") String userId,
                               @PathVariable("file_id") String fileId,
                               HttpServletResponse response) {
        return fileService.download(userId, fileId, response);
    }
}
