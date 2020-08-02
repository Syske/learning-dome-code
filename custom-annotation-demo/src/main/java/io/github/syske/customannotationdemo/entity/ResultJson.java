package io.github.syske.customannotationdemo.entity;

public class ResultJson {
    private int statusCode;
    private String message;
    private boolean isSuccess;
    private Object result;

    public ResultJson() {
    }

    public ResultJson(int statusCode, String message, boolean isSuccess, Object result) {
        this.statusCode = statusCode;
        this.message = message;
        this.isSuccess = isSuccess;
        this.result = result;
    }

    public void setError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.isSuccess = false;
        this.result = null;
    }

    public void setSuccess(Object result) {
        this.statusCode = 200;
        this.message = "请求成功";
        this.isSuccess = true;
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultJson{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                ", result=" + result +
                '}';
    }
}
