package io.github.syske.service;

import io.github.syske.dao.model.ServerInfo;
import com.baomidou.mybatisplus.service.IService;
import io.github.syske.dao.model.ServerPortInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author syske
 * @since 2020-01-20
 */
public interface ServerInfoService extends IService<ServerInfo> {

    List<ServerPortInfo> listServerInfo(ServerPortInfo serverPortInfo);
}
