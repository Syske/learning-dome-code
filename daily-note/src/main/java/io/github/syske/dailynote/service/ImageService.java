package io.github.syske.dailynote.service;

import io.github.syske.dailynote.service.entity.BannerInfo;
import io.github.syske.dailynote.service.entity.NoteBookInfo;
import org.springframework.stereotype.Service;

/**
 * @program: daily-note
 * @description: 图片服务
 * @author: syske
 * @date: 2021-05-02 8:47
 */
public interface ImageService {
    /**
     * 生成每日读书札记图片
     *
     * @param noteBookInfo 读书笔记
     * @return
     */
    String generateDailyNoteCard(NoteBookInfo noteBookInfo);

    /**
     * 生成纯色背景banner
     * @param bannerInfo
     * @return
     */
    String generateBannerPic(BannerInfo bannerInfo);
}
