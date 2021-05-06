package io.github.syske.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.github.syske.dao.model.HostInfo;
import io.github.syske.service.HostInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/hostInfo")
public class HostInfoController {
    @Autowired
    private HostInfoService hostInfoService;

    @RequestMapping("/add")
    public boolean addHostInfo(HostInfo hostInfo) {
        return hostInfoService.insert(hostInfo);
    }

    @RequestMapping("/list")
    public List<HostInfo> listHostInfo(HostInfo hostInfo) {
        EntityWrapper<HostInfo> hostInfoServiceEntityWrapper = new EntityWrapper<>(hostInfo);
        return hostInfoService.selectList(hostInfoServiceEntityWrapper);
    }

    @RequestMapping("/update")
    public boolean updateHostInfo(HostInfo hostInfo) {
        return hostInfoService.updateById(hostInfo);
    }

}

