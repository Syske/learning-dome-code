package io.github.syske.oauth2.demo.entity;

/**
 * @program: springboot-oauth2-demo
 * @description: 角色
 * @author: syske
 * @create: 2020-05-13 16:25
 */
public class Role {
    // 角色ID
    private String id;
    // 角色名
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
