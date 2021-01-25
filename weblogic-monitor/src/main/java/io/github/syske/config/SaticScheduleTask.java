package io.github.syske.config;

import io.github.syske.dao.model.ServerPortInfo;
import io.github.syske.dao.model.ServerRunrecord;
import io.github.syske.service.ServerInfoService;
import io.github.syske.service.ServerRunrecordService;
import io.github.syske.weblogic.WeblogicMonitorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @program: weblogic-monitor
 * @description: 定时任务
 * @author: syske
 * @create: 2020-01-17 11:40
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    private Logger logger = LoggerFactory.getLogger(SaticScheduleTask.class);

    @Autowired
    private ServerInfoService serverInfoService;

    @Autowired
    private ServerRunrecordService serverRunrecordService;


    /**
     * 每120秒获取并保存服务运行记录
     */
    @Scheduled(fixedRate = 120000)
    public void getServerData() {
        ServerPortInfo serverPortInfo = new ServerPortInfo();
        List<ServerPortInfo> serverPortInfos = serverInfoService.listServerInfo(serverPortInfo);
        for (ServerPortInfo portInfo : serverPortInfos) {
            ServerRunrecord serverInfo = WeblogicMonitorUtil.getServerInfo(portInfo.getIp(), portInfo.getPort(),
                    portInfo.getUsername(), portInfo.getPassword());
            serverInfo.setServerId(portInfo.getId());
            serverInfo.setHostId(portInfo.getHostId());
            serverInfo.setRecordtime(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
           // logger.info("服务器运行数据:{}",serverInfo.toString());
            serverRunrecordService.insert(serverInfo);
        }
    }
}
