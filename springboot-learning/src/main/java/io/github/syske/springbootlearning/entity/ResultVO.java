/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootlearning.entity;

/**
 * @author syske
 * @version 1.0
 * @date 2021-09-16 8:31
 */
public class ResultVO {
    private int code;
    private boolean success;
    private String msg;
    private Object result;
    private Long timeStamp;

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

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
