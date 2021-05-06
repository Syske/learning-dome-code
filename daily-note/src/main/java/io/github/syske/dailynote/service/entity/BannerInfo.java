package io.github.syske.dailynote.service.entity;

import io.github.syske.dailynote.util.ChineseColorEnum;

/**
 * @program: daily-note
 * @description: 微信公众号banner
 * @author: syske
 * @date: 2021-05-02 10:35
 */
public class BannerInfo {
    /**
     * id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 背景颜色
     */
    private ChineseColorEnum backgroundColorEnum;

    /**
     * 日期
     */
    private String dataStr;

    public BannerInfo() {
    }

    public BannerInfo(Long id, String title, String content, ChineseColorEnum backgroundColor) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.backgroundColorEnum = backgroundColor;
    }

    public Long getId() {
        return id;
    }

    public BannerInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BannerInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BannerInfo setContent(String content) {
        this.content = content;
        return this;
    }

    public ChineseColorEnum getBackgroundColorEnum() {
        return backgroundColorEnum;
    }

    public BannerInfo setBackgroundColorEnum(ChineseColorEnum backgroundColorEnum) {
        this.backgroundColorEnum = backgroundColorEnum;
        return this;
    }

    public String getDataStr() {
        return dataStr;
    }

    public BannerInfo setDataStr(String dataStr) {
        this.dataStr = dataStr;
        return this;
    }

    @Override
    public String toString() {
        return "BannerInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", backgroundColorEnum=" + backgroundColorEnum +
                ", dataStr='" + dataStr + '\'' +
                '}';
    }
}
