/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.starter.demo.common;

/**
 * 平台枚举
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-10 16:04
 */
public enum MessagePlatformEnum {
    /**
     * 微信
     */
    WeChat(1),
    /**
     * 企业微信
     */
    QyWeChat(2),
    /**
     * 钉钉
     */
    DingDing(3),
    /**
     * 飞书
     */
    FeiShu(4),
    /**
     * pc端
     */
    PC(5),
    /**
     * 移动端
     */
    mobile(6);

    /**
     * 平台id
     */
    private Integer platformId;
    MessagePlatformEnum(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer value() {
        return platformId;
    }
}
