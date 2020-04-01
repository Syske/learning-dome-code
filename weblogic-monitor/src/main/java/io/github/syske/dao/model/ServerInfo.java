package io.github.syske.dao.model;

import com.baomidou.mybatisplus.annotations.TableField;
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
@TableName("server_info")
public class ServerInfo extends Model<ServerInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 端口id
     */
    private String id;
    /**
     * 主机id
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
    /**
     * 备注
     */
    private String comments;


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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
        ", id=" + id +
        ", hostId=" + hostId +
        ", port=" + port +
        ", description=" + description +
        ", comments=" + comments +
        "}";
    }
}
