package io.github.syske.springbootjwtdemo.dao.entity;

import java.io.Serializable;
/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-09 23:59
 */
public class User implements Serializable {
    private String name;
    private String username;
    private String password;
    private String role;
    private String permission;
    // 用户标识
    private int Valid;
    private String salt;

    public User() {

    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getValid() {
        return Valid;
    }

    public void setValid(int valid) {
        Valid = valid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
