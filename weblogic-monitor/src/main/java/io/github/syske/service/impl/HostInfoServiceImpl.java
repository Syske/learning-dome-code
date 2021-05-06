package io.github.syske.service.impl;

import io.github.syske.dao.model.HostInfo;
import io.github.syske.dao.mapper.HostInfoMapper;
import io.github.syske.service.HostInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author syske
 * @since 2020-01-20
 */
@Service
public class HostInfoServiceImpl extends ServiceImpl<HostInfoMapper, HostInfo> implements HostInfoService {

}
