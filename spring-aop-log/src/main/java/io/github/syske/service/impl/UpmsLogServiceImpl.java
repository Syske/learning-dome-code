package io.github.syske.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.github.syske.dao.UpmsLogMapper;
import io.github.syske.entity.UpmsLog;
import io.github.syske.service.UpmsLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author syske
 * @since 2019-11-30
 */
@Service
public class UpmsLogServiceImpl extends ServiceImpl<UpmsLogMapper, UpmsLog> implements UpmsLogService {

}
