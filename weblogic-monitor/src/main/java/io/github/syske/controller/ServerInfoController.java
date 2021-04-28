package io.github.syske.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.github.syske.dao.model.ServerInfo;
import io.github.syske.dao.model.ServerPortInfo;
import io.github.syske.dao.model.ServerRunrecord;
import io.github.syske.dao.model.dto.CurrentRunningStateVo;
import io.github.syske.service.ServerInfoService;
import io.github.syske.weblogic.WeblogicMonitorUtil;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author syske
 * @since 2020-01-20
 */
@RestController
@RequestMapping("/serverInfo")
public class ServerInfoController {

    private Logger logger = LoggerFactory.getLogger(ServerInfoController.class);


    @Autowired
    private ServerInfoService serverInfoService;

    @Autowired
    private MapperFacade mapperFacade;

    @RequestMapping("/add")
    public String add(ServerInfo serverPort) {
        return serverInfoService.insert(serverPort) ? "true" : "false";
    }

    @RequestMapping("/list")
    public String list(ServerInfo serverInfo) {
        Wrapper<ServerInfo> queryWrapper = new EntityWrapper<>(serverInfo);
        return JSON.toJSONString(serverInfoService.selectList(queryWrapper));
    }

    @RequestMapping("/listPortInfo")
    public String listPortInfo(ServerPortInfo serverPortInfo) {
        return JSON.toJSONString(serverInfoService.listServerInfo(serverPortInfo));
    }

    /**
     * 获取实时服务器运行数据
     *
     * @return
     */
    @RequestMapping("/listCurrentRunningData")
    public String listCurrentRunningData() {
        ServerPortInfo serverPortInfo = new ServerPortInfo();
        List<ServerPortInfo> serverPortInfos = serverInfoService.listServerInfo(serverPortInfo);
        List<CurrentRunningStateVo> serverRunrecords = new ArrayList<>();
        for (ServerPortInfo portInfo : serverPortInfos) {
            ServerRunrecord serverInfo = WeblogicMonitorUtil.getServerInfo(portInfo.getIp(), portInfo.getPort(),
                    portInfo.getUsername(), portInfo.getPassword());
            serverInfo.setServerId(portInfo.getId());
            serverInfo.setHostId(portInfo.getHostId());
            serverInfo.setRecordtime(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            CurrentRunningStateVo map = mapperFacade.map(serverInfo, CurrentRunningStateVo.class);
            map.setPortName(portInfo.getName());
            map.setIp(portInfo.getIp());
            map.setPort(portInfo.getPort());
            serverRunrecords.add(map);
            // logger.info("服务器运行数据:{}",serverInfo.toString());
        }
        serverRunrecords.sort((serverRunrecord1, serverRunrecord2) -> {
            if (serverRunrecord1.getServerName().equals(serverRunrecord2.getServerName())) {
                return serverRunrecord1.getIp().compareTo(serverRunrecord2.getIp());
            } else {
                return serverRunrecord1.getServerName().compareTo(serverRunrecord1.getServerName());
            }
        });
        return JSON.toJSONString(serverRunrecords);
    }
}

