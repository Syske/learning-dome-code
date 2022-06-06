package io.github.syske.mybatis.datasource.dao;

import io.github.syske.mybatis.datasource.entity.UserEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: spring-mybatis-dynamic-datasource-demo
 * @description:
 * @author: syske
 * @date: 2022-04-10 17:27
 */
@Repository
public interface UserMapper extends Mapper<UserEntity> {

}
