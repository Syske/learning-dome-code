package io.github.syske.springwebfluxdemo.repository.entity;

/**
 * @program: spring-webflux-demo
 * @description: user
 * @author: syske
 * @date: 2021-07-30 13:12
 */
public class User {
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
