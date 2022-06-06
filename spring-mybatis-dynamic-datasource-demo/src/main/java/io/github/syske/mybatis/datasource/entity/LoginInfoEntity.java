package io.github.syske.mybatis.datasource.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @program: spring-mybatis-dynamic-datasource-demo
 * @description: 登录信息
 * @author: syske
 * @date: 2022-04-10 17:25
 */
@Table(name = "login_info")
public class LoginInfoEntity {

    @Column
    private Long id;

    @Column
    private String account;

    /**
     * 获取 account 的值
     *
     * @return 返回 account 的值
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置 account 的值
     *
     * @param account account
     */
    public void setAccount(String account) {
        this.account = account;
    }

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

    @Override
    public String toString() {
        return "LoginInfoEntity{" +
                "id=" + id +
                ", account='" + account + '\'' +
                '}';
    }
}
