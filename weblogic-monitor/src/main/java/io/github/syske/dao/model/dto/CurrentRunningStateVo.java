package io.github.syske.dao.model.dto;


import java.io.Serializable;

/**
 * 实时服务运行状态
 *
 * @program: weblogic-monitor
 * @description:
 * @author: syske
 * @create: 2020-01-19 18:47
 */

public class CurrentRunningStateVo implements Serializable {
    /**
     * 服务器id
     */
    private String serverId;
    /**
     * 端口id
     */
    private String portId;
    /**
     * 服务名
     */
    private String serverName;
    /**
     * 服务描述
     */
    private String portDescription;
    /**
     * 服务状态
     */
    private String state;
    /**
     * 上次启动时间
     */
    private String lastStartTime;

    /**
     * 端口名称
     */
    private String portName;

    /**
     * 主机ip
     */
    private String ip;

    /**
     * 服务端口
     */
    private String port;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPortDescription() {
        return portDescription;
    }

    public void setPortDescription(String portDescription) {
        this.portDescription = portDescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(String lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "CurrentRunningStateVo{" +
                "serverId='" + serverId + '\'' +
                ", portId='" + portId + '\'' +
                ", serverName='" + serverName + '\'' +
                ", portDescription='" + portDescription + '\'' +
                ", state='" + state + '\'' +
                ", lastStartTime='" + lastStartTime + '\'' +
                ", portName='" + portName + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
