package io.github.syske.mybatis.datasource.dao;

import io.github.syske.mybatis.datasource.entity.LoginInfoEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: spring-mybatis-dynamic-datasource-demo
 * @description:
 * @author: syske
 * @date: 2022-04-10 17:32
 */
@Repository
public interface LoginInfoMapper extends Mapper<LoginInfoEntity> {
}
