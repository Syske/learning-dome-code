package io.igthub.syske.springbootstrategypattern.entity;

import java.io.Serializable;

/**
 * @program: springboot-strategy-pattern-demo
 * @description: 请求参数
 * @author: syske
 * @create: 2020-09-12 10:11
 */
public class WrapperRequest<T> implements Serializable {
    private RequestHeader header;
    private RequestBody<T> body;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public RequestBody<T> getBody() {
        return body;
    }

    public void setBody(RequestBody<T> body) {
        this.body = body;
    }
}
