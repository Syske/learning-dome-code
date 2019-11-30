package io.github.syske.mybatisplus.service.impl;

import io.github.syske.mybatisplus.dao.model.User;
import io.github.syske.mybatisplus.dao.mapper.UserMapper;
import io.github.syske.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syske
 * @since 2019-11-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
