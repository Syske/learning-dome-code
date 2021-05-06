package io.github.syske.service.impl;

import io.github.syske.dao.model.ServerInfo;
import io.github.syske.dao.mapper.ServerInfoMapper;
import io.github.syske.dao.model.ServerPortInfo;
import io.github.syske.service.ServerInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author syske
 * @since 2020-01-20
 */
@Service
public class ServerInfoServiceImpl extends ServiceImpl<ServerInfoMapper, ServerInfo> implements ServerInfoService {

    @Autowired
    private ServerInfoMapper serverInfoMapper;

    @Override
    public List<ServerPortInfo> listServerInfo(ServerPortInfo serverPortInfo) {
        return serverInfoMapper.listServerInfo(serverPortInfo);
    }
}
