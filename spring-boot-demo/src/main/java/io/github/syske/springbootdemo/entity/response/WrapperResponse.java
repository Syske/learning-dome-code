package io.github.syske.springbootdemo.entity.response;

import java.util.List;

/**
 * @program: httputil-demo
 * @description: 返回值
 * @author: syske
 * @create: 2020-05-14 15:11
 *
 * | 序号 | 名称     | 字段    | 类型   | 值范围 | 说明                     |
 * | ---- | -------- | ------- | ------ | ------ | ------------------------ |
 * | 1    | 返回值   | code    | 字符串 | 12     | 服务返回值：是否成功     |
 * | 2    | 业务类型 | Type    | 字符串 | 12     | 业务操作类型             |
 * | 3    | 返回信息 | message | 字符串 | 512    | 服务返回信息，提示、错误 |
 * | 4    | 返回数据 | Data    | 泛型   |        | 泛型，存储返回数据       |
 */
public class WrapperResponse<T> {
    private String code;
    private String Type;
    private String message;
    private List<T> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public WrapperResponse<Object> error(String errorMessage) {
        WrapperResponse<Object> objectWrapperResponse = new WrapperResponse<>();
        objectWrapperResponse.code = "00001";
        objectWrapperResponse.data = null;
        objectWrapperResponse.message = errorMessage;
        objectWrapperResponse.Type = "ERROR";
        return objectWrapperResponse;
    }

    @Override
    public String toString() {
        return "WrapperResponse{" +
                "code='" + code + '\'' +
                ", Type='" + Type + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
