/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springwebfluxdemo.vo;

/**
 * @author syske
 * @version 1.0
 * @date 2021-07-30 8:52
 */
public class UserVo {
    private Long id;

    private String username;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
