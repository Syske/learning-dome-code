package io.github.syske.websigndemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.syske.websigndemo.dto.User;
import io.github.syske.websigndemo.entity.AjaxJson;
import io.github.syske.websigndemo.service.SignTestService;
import io.github.syske.websigndemo.util.Base64Util;
import io.github.syske.websigndemo.util.ImageUtil;
import io.github.syske.websigndemo.util.PdfUtil;
import io.github.syske.websigndemo.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SignTestServiceImpl implements SignTestService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public AjaxJson generatePersonalCommitment(String params) {

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setMsg("调用成功！");
        JSONObject results = new JSONObject();
        results.put("success", true);
        JSONObject resultData = new JSONObject();
        try {

            // 个人承诺书imgbase64
            String generatePersonalCommitmentImg = generatePersonalCommitmentImg(params);
            results.put("personalCommitment", generatePersonalCommitmentImg);
            resultData.put("datas", results.getString("personalCommitment"));

            ajaxJson.setObj(resultData);

        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(e.getMessage());
        }
        return ajaxJson;
    }

    @Override
    public AjaxJson signPersonalCommitment(String params) {

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setMsg("调用成功！");
        JSONObject results = new JSONObject();
        results.put("success", true);
        JSONObject resultData = new JSONObject();
        try {
            // 个人承诺书imgbase64
            String signPersonalCommitmentImg = signPersonalCommitmentImg(params);
            results.put("personalCommitmentSign", signPersonalCommitmentImg);
            resultData.put("datas", results.getString("personalCommitmentSign"));
            ajaxJson.setObj(resultData);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(e.getMessage());
        }
        return ajaxJson;
    }

    /**
     * 生成个人承诺书参数集
     *
     * @param user
     * @return
     */
    private Map<String, String> getParameters(User user) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("gender", user.getGender());
        parameters.put("age", user.getAge());
        parameters.put("birthday", user.getBirthday());
        parameters.put("description", user.getDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        parameters.put("date", dateFormat.format(new Date()));
        return parameters;
    }

    /**
     * 返回承诺书imgbase64:未签名版
     *
     * @param params
     * @return
     * @throws Exception
     */
    private String generatePersonalCommitmentImg(String params) throws Exception {
        JSONObject datas = JSONObject.parseObject(params);
        User user = JSON.toJavaObject(datas, User.class);
        // 1. 根据模板生成pdf个人承诺书
        Map<String, String> parameters = getParameters(user);

        String tempPdfFileFullName = "./" + UUIDUtil.getUUIDStr() + ".pdf";
        File tempFile = new File(tempPdfFileFullName);
        logger.debug("文件绝对路径：" + tempFile.getAbsolutePath());
        try {
            // pdf模板
            String templateFilePath = "./pdf-templates/个人承诺书-template.pdf";
            PdfUtil.exportPdf(templateFilePath, tempFile, parameters);
            // 2. pdf转imgBase64，pdf只有一页，所以页码为0
            return PdfUtil.pdf2Pic(tempPdfFileFullName, 0);
        } catch (Exception e) {
            throw new Exception("生成个人承诺书失败");
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * 返回承诺书imgbase64:签名版
     *
     * @param params
     * @return
     * @throws Exception
     */
    private String signPersonalCommitmentImg(String params) throws Exception {
        JSONObject datas = JSONObject.parseObject(params);
        User user = JSON.toJavaObject(datas, User.class);
        String tempFileFullName = "./" + UUIDUtil.getUUIDStr() + ".png";
        File tempFile = new File(tempFileFullName);
        try {
            // 1. 签名
            BufferedImage sourceImage = ImageUtil.getInputStreamFormBase64(user.getFile());
            BufferedImage signImage = ImageUtil.getInputStreamFormBase64(user.getSign());
            BufferedImage bufferedImage1 = ImageUtil.overlyingImage(sourceImage, signImage, 1000, 1600, 1.0f, 50);
            ImageUtil.generateSaveFile(bufferedImage1, tempFileFullName);
            return Base64Util.getFileBase64Str(tempFile);
        } catch (Exception e) {
            throw new Exception("生成个人承诺书失败");
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
