package io.github.syske.springbootjwtdemo.dao.entity;

/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-13 20:38
 */
public class Result<T> {
    private boolean success;
    private int status;
    private Object result;

    public Result() {
    }

    public Result(boolean success, int status, Object result) {
        this.success = success;
        this.status = status;
        this.result = result;
    }

    public void success(Object result) {
        this.success = true;
        this.result = result;
    }

    public void error(Object result) {
        this.result = result;
        this.success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", status=" + status +
                ", result=" + result +
                '}';
    }
}
