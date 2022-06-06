package io.github.syske.mybatis.datasource.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @program: spring-mybatis-dynamic-datasource-demo
 * @description: 消息
 * @author: syske
 * @date: 2022-04-10 17:23
 */
@Table(name = "message")
public class MessageEntity {
    @Column
    private Long id;

    @Column
    private String content;

    /**
     * 获取 id 的值
     *
     * @return 返回 id 的值
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置 id 的值
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 content 的值
     *
     * @return 返回 content 的值
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置 content 的值
     *
     * @param content content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
