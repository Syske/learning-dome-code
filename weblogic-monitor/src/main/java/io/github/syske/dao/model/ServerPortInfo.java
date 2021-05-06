package io.github.syske.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

/**
 * @program: weblogic-monitor
 * @description:
 * @author: syske
 * @create: 2020-01-19 16:00
 */

public class ServerPortInfo extends Model<ServerPortInfo> {
    private static final long serialVersionUID = 1L;
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

    /**
     * 端口id
     */
    @TableField("id")
    private String id;
    /**
     * 服务器id
     */
    @TableField("host_id")
    private String hostId;
    /**
     * 服务端口
     */
    private String port;
    /**
     * 服务描述
     */
    private String description;

    @Override
    protected Serializable pkVal() {
        return this.id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ServerPortInfo{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                ", serverId='" + hostId + '\'' +
                ", port='" + port + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
