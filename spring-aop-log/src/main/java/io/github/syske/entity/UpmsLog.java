package io.github.syske.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;

/**
 * @program: spring-ao-plog
 * @description: 日志
 * @author: liu yan
 * @create: 2019-11-30 21:55
 */




public class UpmsLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableField("LOG_ID")
    private String logId;
    /**
     * 操作描述
     */
    @TableField("DESCRIPTION")
    private String description;
    /**
     * 操作用户
     */
    @TableField("USERNAME")
    private String username;
    /**
     * 操作时间
     */
    @TableField("START_TIME")
    private Long startTime;
    /**
     * 消耗时间
     */
    @TableField("SPEND_TIME")
    private Long spendTime;
    /**
     * 根路径
     */
    @TableField("BASE_PATH")
    private String basePath;
    /**
     * URI
     */
    @TableField("URI")
    private String uri;
    /**
     * URL
     */
    @TableField("URL")
    private String url;
    /**
     * 请求类型
     */
    @TableField("METHOD")
    private String method;
    @TableField("PARAMETER")
    private String parameter;
    /**
     * 用户标识
     */
    @TableField("USER_AGENT")
    private String userAgent;
    /**
     * IP地址
     */
    @TableField("IP")
    private String ip;
    @TableField("RESULT_")
    private String result;
    /**
     * 权限值
     */
    @TableField("PERMISSIONS")
    private String permissions;
    /**
     * 生成时间
     */
    @TableField("CREATETIME")
    private String createtime;
    /**
     * 用户id
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 用户id
     */
    @TableField("APPID")
    private String appId;


    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Long spendTime) {
        this.spendTime = spendTime;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "UpmsLog{" +
                "logId=" + logId +
                ", description=" + description +
                ", username=" + username +
                ", startTime=" + startTime +
                ", spendTime=" + spendTime +
                ", basePath=" + basePath +
                ", uri=" + uri +
                ", url=" + url +
                ", method=" + method +
                ", parameter=" + parameter +
                ", userAgent=" + userAgent +
                ", ip=" + ip +
                ", result=" + result +
                ", permissions=" + permissions +
                ", createtime=" + createtime +
                ", userId=" + userId +
                ", appId=" + appId +
                "}";
    }
}