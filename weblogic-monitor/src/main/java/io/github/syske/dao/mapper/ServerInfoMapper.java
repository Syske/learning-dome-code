package io.github.syske.dao.mapper;

import io.github.syske.dao.model.ServerInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.github.syske.dao.model.ServerPortInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author syske
 * @since 2020-01-20
 */
@Repository
public interface ServerInfoMapper extends BaseMapper<ServerInfo> {

    List<ServerPortInfo> listServerInfo(ServerPortInfo serverPortInfo);
}
