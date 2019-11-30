package io.github.syske.mybatisplus.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author syske
 * @since 2019-11-30
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id，唯一主键
     */
    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别，0-女，1-男，2-未知
     */
    private String sex;
    /**
     * 密码
     */
    private String password;
    /**
     * 注册时间
     */
    private String registerTime;
    /**
     * 用户签名
     */
    private String sign;
    /**
     * 用户状态，1-启用，0-禁用
     */
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        ", id=" + id +
        ", userName=" + userName +
        ", nickName=" + nickName +
        ", sex=" + sex +
        ", password=" + password +
        ", registerTime=" + registerTime +
        ", sign=" + sign +
        ", status=" + status +
        "}";
    }
}
