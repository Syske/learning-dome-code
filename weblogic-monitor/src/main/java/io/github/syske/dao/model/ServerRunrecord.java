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
@TableName("server_runrecord")
public class ServerRunrecord extends Model<ServerRunrecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    private String id;
    /**
     * 服务器id
     */
    @TableField("host_id")
    private String hostId;
    /**
     * 端口id
     */
    @TableField("server_id")
    private String serverId;
    /**
     * 服务状态
     */
    private String state;
    /**
     * 记录时间
     */
    private String recordtime;
    /**
     * 上次启动时间
     */
    @TableField("last_start_time")
    private String lastStartTime;
    /**
     * 服务名称
     */
    @TableField("server_name")
    private String serverName;
    /**
     * 服务描述
     */
    private String desecription;


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

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(String lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDesecription() {
        return desecription;
    }

    public void setDesecription(String desecription) {
        this.desecription = desecription;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ServerRunrecord{" +
                ", id=" + id +
                ", hostId=" + hostId +
                ", serverId=" + serverId +
                ", state=" + state +
                ", recordtime=" + recordtime +
                ", lastStartTime=" + lastStartTime +
                ", serverName=" + serverName +
                ", desecription=" + desecription +
                "}";
    }
}
