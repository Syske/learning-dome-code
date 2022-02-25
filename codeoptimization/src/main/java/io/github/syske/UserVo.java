/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske;

/**
 * @author syske
 * @version 1.0
 * @date 2021-12-02 21:31
 */
public class UserVo {
    /**
     * 用户 id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;

    private Integer version;
    private Integer version2;
    private Integer version3;
    private Integer version4;
    private Integer version5;
    private Integer version6;
    private Integer version7;
    private Integer version8;
    private Integer version9;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion2() {
        return version2;
    }

    public void setVersion2(Integer version2) {
        this.version2 = version2;
    }

    public Integer getVersion3() {
        return version3;
    }

    public void setVersion3(Integer version3) {
        this.version3 = version3;
    }

    public Integer getVersion4() {
        return version4;
    }

    public void setVersion4(Integer version4) {
        this.version4 = version4;
    }

    public Integer getVersion5() {
        return version5;
    }

    public void setVersion5(Integer version5) {
        this.version5 = version5;
    }

    public Integer getVersion6() {
        return version6;
    }

    public void setVersion6(Integer version6) {
        this.version6 = version6;
    }

    public Integer getVersion7() {
        return version7;
    }

    public void setVersion7(Integer version7) {
        this.version7 = version7;
    }

    public Integer getVersion8() {
        return version8;
    }

    public void setVersion8(Integer version8) {
        this.version8 = version8;
    }

    public Integer getVersion9() {
        return version9;
    }

    public void setVersion9(Integer version9) {
        this.version9 = version9;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
