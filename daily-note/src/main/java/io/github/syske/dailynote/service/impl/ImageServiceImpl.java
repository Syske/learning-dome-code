package io.github.syske.dailynote.service.impl;

import cn.hutool.core.util.StrUtil;
import io.github.syske.dailynote.service.ImageService;
import io.github.syske.dailynote.service.entity.BannerInfo;
import io.github.syske.dailynote.service.entity.NoteBookInfo;
import io.github.syske.dailynote.util.ChineseColorEnum;
import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Date;

/**
 * @program: daily-note
 * @description: 图片服务实现类
 * @author: syske
 * @date: 2021-05-02 8:48
 */
@Service
public class ImageServiceImpl implements ImageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 二维码路径
     */
    private final  String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
    /**
     * 脚注内容
     */
    private final String footerContent = "-【每日读书札记】-";
    private ImageUtil imageUtil = new ImageUtil();

    @Override
    public String generateDailyNoteCard(@NonNull NoteBookInfo noteBookInfo) {
        try {
            String authorName = noteBookInfo.getAuthor();
            StringBuilder authorInfo = new StringBuilder("—— ")
                    .append(authorName);
            String bookTitle = noteBookInfo.getBookTitle();
            if (StrUtil.isNotBlank(bookTitle)) {
                authorInfo.append("《")
                        .append(bookTitle)
                        .append("》");
            }
            Date date = new Date();
            String uuidStr = UUIDUtil.getUUIDStr();
            String imgSaveFullPath = "D:\\tmp\\img\\created\\" + uuidStr + ".jpg";
            String faceImgSaveFullPath = "D:\\tmp\\img\\created\\face-img-" + uuidStr + ".jpg";
            String bannerPicUrl = noteBookInfo.getBannerPicUrl();
            imageUtil.createReadingNoteCard(qrCodeImgPath, imgSaveFullPath, bannerPicUrl, noteBookInfo.getNoteContent(), authorInfo.toString(), footerContent, date);
            imageUtil.generateBannerPic(authorName, bannerPicUrl, faceImgSaveFullPath);
        } catch (Exception e) {
            logger.error("生成每日读书札记出错", e);
        }
        return null;
    }

    @Override
    public String generateBannerPic(BannerInfo bannerInfo) {
        String imgSaveFullPath = "D:\\face-img-" + UUIDUtil.getUUIDStr() + ".jpg";
        Color fontColor = null;
        ChineseColorEnum backgroundColorEnum = bannerInfo.getBackgroundColorEnum();
        imageUtil.generateBannerPic(bannerInfo.getTitle(), backgroundColorEnum ,
                imgSaveFullPath, bannerInfo.getDataStr());
        return null;
    }
}
