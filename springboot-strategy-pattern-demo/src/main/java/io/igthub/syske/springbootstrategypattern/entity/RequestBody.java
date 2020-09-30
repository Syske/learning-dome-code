package io.igthub.syske.springbootstrategypattern.entity;


/**
 * 请求体
 * @param <T>
 */
public class RequestBody<T> {
    private T parameter;

    public T getParameter() {
        return parameter;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }
}
