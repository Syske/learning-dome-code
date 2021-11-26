package io.github.syske;

/**
 * @program: lambda-demo
 * @description:
 * @author: syske
 * @date: 2021-11-26 13:16
 */
public class User {
    /**
     * 用户 id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;



    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * 获取 id.
     *
     * @return 返回 id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置 id 的值
     *
     * <p>可以通过 getId() 来获取 id 的值</p>
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 username.
     *
     * @return 返回 username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置 username 的值
     *
     * <p>可以通过 getUsername() 来获取 username 的值</p>
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
