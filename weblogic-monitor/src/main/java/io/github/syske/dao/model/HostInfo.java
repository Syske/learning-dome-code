package io.github.syske.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author syske
 * @since 2020-01-20
 */
@TableName("host_info")
public class HostInfo extends Model<HostInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主机id
     */
    private String id;
    /**
     * 主机名
     */
    private String name;
    /**
     * 主机ip
     */
    private String ip;
    /**
     * 控制台登陆用户名
     */
    private String username;
    /**
     * 控制台登陆密码
     */
    private String password;


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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HostInfo{" +
        ", id=" + id +
        ", name=" + name +
        ", ip=" + ip +
        ", username=" + username +
        ", password=" + password +
        "}";
    }
}
