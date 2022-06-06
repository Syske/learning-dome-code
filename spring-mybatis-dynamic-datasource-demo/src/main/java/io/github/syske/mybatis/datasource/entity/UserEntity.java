package io.github.syske.mybatis.datasource.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @program: spring-mybatis-dynamic-datasource-demo
 * @description: 用户
 * @author: syske
 * @date: 2022-04-10 17:26
 */
@Table(name = "user")
public class UserEntity {

    @Column
    private Long id;

    @Column
    private String name;

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
     * 获取 name 的值
     *
     * @return 返回 name 的值
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 name 的值
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
