package io.github.syske.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.github.syske.dao.model.ServerRunrecord;
import io.github.syske.service.ServerRunrecordService;
import io.github.syske.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author syske
 * @since 2020-01-20
 */
@RestController
@RequestMapping("/serverRunrecord")
public class ServerRunrecordController {

    @Autowired
    private ServerRunrecordService runrecordService;


    @RequestMapping("/list")
    public String listRunrecord(ServerRunrecord serverRunrecord) {
        EntityWrapper<ServerRunrecord> serverRunrecordEntityWrapper = new EntityWrapper<>(serverRunrecord);
        serverRunrecordEntityWrapper.ge("recordtime", DateTimeUtil.getCurrentDateTime("yyyyMMdd0000000")) // 大于等于
                .and().le("recordtime", DateTimeUtil.getCurrentDateTime("yyyyMMddHHmmssSSS")) // 小于等于
                .orderBy("recordtime", false);   // 排序
        return JSON.toJSONString(runrecordService.selectList(serverRunrecordEntityWrapper));
    }

}

