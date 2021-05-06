package io.github.syske.springbootjdbcmybatisdemo.entity;

/**
 * @program: springboot-jdbc-mybatis-demo
 * @description:
 * @author: syske
 * @create: 2020-08-25 17:30
 */
public class User {
    private String account_id;
    private String personnel_id;
    private String app_id;
    private String first_name;
    private String password;
    private String blocked;
    private String description;
    private String sysrole;
    private String data_part_id;
    private String operacct;
    private String operator;
    private String operatetime;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getPersonnel_id() {
        return personnel_id;
    }

    public void setPersonnel_id(String personnel_id) {
        this.personnel_id = personnel_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSysrole() {
        return sysrole;
    }

    public void setSysrole(String sysrole) {
        this.sysrole = sysrole;
    }

    public String getData_part_id() {
        return data_part_id;
    }

    public void setData_part_id(String data_part_id) {
        this.data_part_id = data_part_id;
    }

    public String getOperacct() {
        return operacct;
    }

    public void setOperacct(String operacct) {
        this.operacct = operacct;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(String operatetime) {
        this.operatetime = operatetime;
    }
}
