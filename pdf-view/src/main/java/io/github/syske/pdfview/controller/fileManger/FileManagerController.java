package io.github.syske.pdfview.controller.fileManger;


import io.github.syske.pdfview.entity.AjaxJson;
import io.github.syske.pdfview.exception.FileDownloadException;
import io.github.syske.pdfview.utils.Base64Util;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: publicCoreServer
 * @description:
 * @author: syske
 * @create: 2020-03-18 17:47
 */
@Api(value = "file", description = "文件管理")
@RestController
@RequestMapping("pc/file")
public class FileManagerController {

    private String remotUrl = "http://10.190.131.118/";

    @RequestMapping("/download")
    public AjaxJson downLoad(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return new AjaxJson(new Exception("文件名不能为空"));
        }

        String base64 = Base64Util.remotePdfToBase64(remotUrl + fileName);
        AjaxJson result = new AjaxJson("请求成功", true);
        result.setObj(base64);
        return result;
    }
}