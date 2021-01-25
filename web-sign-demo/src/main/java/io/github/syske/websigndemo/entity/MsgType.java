package io.github.syske.websigndemo.entity;

/**
 * @program: public-server
 * @description:
 * @author: syske
 * @create: 2020-03-06 22:42
 */

public enum MsgType {
    info,
    warning,
    error,
    question;

    private MsgType() {
    }
}
