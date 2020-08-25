package io.github.syske.springbootlearning.entity;

import java.util.HashMap;
import java.util.Map;

public class Result {
    private int code;
    private boolean success;
    private String msg;
    private Object result;

    public Result() {
    }

    public Result(int code, boolean success, String msg, Object result) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.result = result;
    }

    public static Result getSuccess(Object result) {
        return new Result(1, true, "请求成功", result);
    }

    public static Result getFailed(String msg, Object result) {
        return new Result(0, false, msg, result);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
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
