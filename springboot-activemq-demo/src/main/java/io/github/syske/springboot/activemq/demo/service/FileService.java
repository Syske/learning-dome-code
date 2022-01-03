package io.github.syske.springboot.activemq.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.github.syske.springboot.activemq.demo.util.RedisUtil;
import io.github.syske.springboot.activemq.demo.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: springboot-activemq-demo
 * @description: activeMq服务
 * @author: syske
 * @date: 2021-05-22 23:40
 */
@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private JmsSendService jmsSendService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 文件导出
     *
     * @param name
     * @param userId
     * @return
     */
    public JSONObject export(String userId, String name) {

        JSONObject result = new JSONObject();
        result.put("userId", userId);
        result.put("type", 0);
        String uuId = UUIDUtil.getUUId();
        result.put("fileId", uuId);
        result.put("name", name);
        // 异步导出文件
        doExport(result);
        result.put("success", true);
        result.put("code", 0);
        result.put("message", "数据导出提交成功，请稍后到文件中心下载！");
        return result;
    }

    /**
     * 下载文件
     *
     * @param userId
     * @param fileId
     * @return
     */
    public JSONObject download(String userId, String fileId, HttpServletResponse response) {
        String fileKey = String.format("fileExport.%s.%s", userId, fileId);
        JSONObject result = new JSONObject();
        if (!redisUtil.existKey(fileKey)) {
            result.put("message", "文件导出尚未完成，请稍后重试");
            result.put("code", -1);
            result.put("success", false);
            return result;
        }
        String fileString = redisUtil.getString(fileKey);
        JSONObject fileJson = JSON.parseObject(fileString);
        String path = fileJson.getString("path");
        Boolean isDownload = fileJson.getBoolean("isDownload");

        // 校验文件是否已下载
        if (isDownload) {
            result.put("message", "文件下载失败，文件已下载");
            result.put("code", -1);
            result.put("success", false);
            return result;
        }
        File file = new File(path);
        responseFile(response, result, file);
        result.put("success", true);
        result.put("code", 0);
        result.put("message", "文件下载成功");
        fileJson.put("isDownload", true);
        fileJson.put("downloadTime", System.currentTimeMillis());
        // 更新文件状态
        redisUtil.setString(fileKey, fileJson.toJSONString());
        return result;
    }

    private void responseFile(HttpServletResponse response, JSONObject result, File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
            response.addHeader("Content-Length", "" + file.length());
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int read;
            while ((read = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes);
            }
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            logger.error("文件下载失败", e);
            result.put("message", "文件下载失败");
            result.put("code", -1);
            result.put("success", false);
        }
    }

    @Async("taskExecutor")
    void doExport(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            if (StringUtils.hasLength(name)) {
                String userId = jsonObject.getString("userId");
                String uuId = jsonObject.getString("fileId");
                // 其他数据校验，这里通过睡眠模拟
                Thread.sleep(1000L);
                // 组装保存文件信息
                jsonObject.put("type", 0);
                jsonObject.put("isDownload", false);
                jsonObject.put("createTime", System.currentTimeMillis());
                // 保存文件数据，实际业务中，这部分应该是存在数据库里的，这里为了演示方便，直接存在数据库里了
                redisUtil.setString(String.format("fileExport.%s.%s", userId, uuId), jsonObject.toJSONString());
                // 发送文件导出业务消息
                jmsSendService.sendMessage("file-export-queue", jsonObject.toJSONString());
            }
        } catch (Exception e) {
            logger.error("数据导出错误", e);
        }
    }

    @JmsListener(destination = "file-export-queue", containerFactory = "jmsListenerContainerFactory")
    public void testMq(String message) {
        logger.info("文件导出业务入参：{}", message);
        JSONObject messageJsonObject = JSON.parseObject(message);
        Integer type = messageJsonObject.getInteger("type");
        if (type == 0) {
            Object fileId = messageJsonObject.get("fileId");
            Object userId = messageJsonObject.get("userId");
            String filePath = String.format("./%s.txt", fileId);
            messageJsonObject.put("path", filePath);
            String fileKey = String.format("fileExport.%s.%s", userId, fileId);
            // 查询数据
            List<String> dataList = Lists.newArrayList("张三", "历史", "周三");
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                for (String s : dataList) {
                    fileOutputStream.write(s.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                }
                redisUtil.setString(fileKey, messageJsonObject.toJSONString());
            } catch (Exception e) {
                logger.error("文件导出失败", e);
            }
        }

    }

    public String delayMessage() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        jmsSendService.sendDelayMessage("delay-message", "hello delay " + dateFormat.format(new Date()));
        return "true";
    }

    @JmsListener(destination = "delay-message", containerFactory = "jmsListenerContainerFactory")
    public void dealDelayMessage(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        logger.info("delay-message 收到消息： {}, timestamp：{}", message, dateFormat.format(new Date()));
    }

    public String normalMessage() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        jmsSendService.sendMessage("normal-message", "normal message " + dateFormat.format(new Date()));
        return "true";
    }

    @JmsListener(destination = "normal-message", containerFactory = "jmsListenerContainerFactory")
    public void dealNormalMessage(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        logger.info("normal-message 收到消息： {}, timestamp：{}", message, dateFormat.format(new Date()));
    }

    public String delayMessage2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        jmsSendService.sendDelayMessage2("delay-message", "delay2 kkkkkk " + dateFormat.format(new Date()));
        return "true";
    }

}
