package io.github.syske.cacheserver.dto;

/**
 * @program: cache-server
 * @description: 返回结果
 * @author: syske
 * @create: 2021-02-23 11:41
 */
public class Result<T> {
    /**
     * 请求是否成功
     */
    private boolean success;
    /**
     * 响应状态
     */
    private int status;
    /**
     * 返回结果
     */
    private T result;
    /**
     * 消息信息
     */
    private String message;

    public Result() {
    }

    public Result(boolean success, int status, String message, T result) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.result = result;
    }

    public static Result getFailed(String errorMessage, Object result) {
        return new Result(false, 0, errorMessage, result);
    }

    public void success(T result) {
        this.success = true;
        this.result = result;
    }

    public void error(T result) {
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

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
