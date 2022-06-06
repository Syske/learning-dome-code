package io.github.syske.mybatis.datasource.dao;

import io.github.syske.mybatis.datasource.entity.MessageEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: spring-mybatis-dynamic-datasource-demo
 * @description:
 * @author: syske
 * @date: 2022-04-10 17:29
 */
@Repository
public interface MessageMapper extends Mapper<MessageEntity> {
}
