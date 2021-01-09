package io.igthub.syske.springbootstrategypattern.entity;

/**
 * 请求头
 *
 *
 */
public class RequestHeader {
    // 交易编码
    private String businessCode;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
