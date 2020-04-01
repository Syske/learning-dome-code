package io.github.syske.springbootjwtdemo.dao.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-09 23:58
 */
public class ReturnEntity {
    // 成功标志 1 表示成功，0 表示失败
    private int code;
    // 消息
    private String message;
    // 结果
    private Object data;
    // 业务类型 1 用户权限相关（登陆、权限校验等） 2 其他
    private int codeType;


    public ReturnEntity() {
    }

    public ReturnEntity(int code, int codeType, String message, Object data) {
        this.code = code;
        this.codeType = codeType;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCodeType() {
        return codeType;
    }

    public void setCodeType(int codeType) {
        this.codeType = codeType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ReturnEntity successResult(int codeType, Object data) {
        return new ReturnEntity(1,codeType,"请求成功", data);
    }

    public static ReturnEntity failedResult(int codeType, String message) {
        return new ReturnEntity(0, codeType, message, false);
    }

    public static Map<String, Object> failedResultMap(int codeType, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("codeType", codeType);
        result.put("message", message);
        result.put("data", false);
        return result;
    }

}
