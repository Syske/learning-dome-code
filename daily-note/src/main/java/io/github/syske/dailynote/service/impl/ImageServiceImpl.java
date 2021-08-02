package io.github.syske.dailynote.service.impl;

import cn.hutool.core.util.StrUtil;
import io.github.syske.dailynote.service.ImageService;
import io.github.syske.dailynote.service.entity.BannerInfo;
import io.github.syske.dailynote.service.entity.NoteBookInfo;
import io.github.syske.dailynote.util.ChineseColorEnum;
import io.github.syske.dailynote.util.DateUtil;
import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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

    @Value("${note.card.image.save.path}")
    private String noteCardSavePath;

    @Value("${note.card.face.image.save.path}")
    private String faceImgSave;

    private final String PATH_LINE = "\\";

    private final String FACE_IMG_PREFIX = "face-img-";

    private void initSavePath() {
        File savePath = new File(noteCardSavePath + DateUtil.getDateYearMothStr() + PATH_LINE);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
    }

    @Override
    public String generateDailyNoteCard(@NonNull NoteBookInfo noteBookInfo) {
        initSavePath();
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
            String imgSaveFullPath = noteCardSavePath + DateUtil.getDateYearMothStr() + PATH_LINE + DateUtil.getDatestrYYYY_MM_dd() + ".jpg";
            String faceImgSaveFullPath = faceImgSave + DateUtil.getDatestrYYYY_MM_dd() + ".jpg";
            String bannerPicUrl = noteBookInfo.getBannerPicUrl();
            imageUtil.createReadingNoteCard(qrCodeImgPath, imgSaveFullPath, bannerPicUrl, noteBookInfo.getNoteContent(), authorInfo.toString(), footerContent, date);
            StringBuilder content = new StringBuilder("每日读书札记");
            if (!StringUtils.isEmpty(bookTitle)) {
                content.append(" | ").append(bookTitle);
            }
            imageUtil.generateBannerPic(content.toString(), bannerPicUrl, faceImgSaveFullPath);
        } catch (Exception e) {
            logger.error("生成每日读书札记出错", e);
        }
        return null;
    }

    @Override
    public String generateBannerPic(BannerInfo bannerInfo) {
        String imgSaveFullPath = faceImgSave + FACE_IMG_PREFIX + UUIDUtil.getUUIDStr() + ".jpg";
        Color fontColor = null;
        ChineseColorEnum backgroundColorEnum = bannerInfo.getBackgroundColorEnum();
        imageUtil.generateBannerPic(bannerInfo.getTitle(), backgroundColorEnum ,
                imgSaveFullPath);
        return null;
    }

    @Override
    public String generateBannerPicWithImage(String title, String bannerPicUrl) {
        String imgSaveFullPath =  faceImgSave + FACE_IMG_PREFIX  + UUIDUtil.getUUIDStr() + ".jpg";
        try {
            imageUtil.generateBannerPic(title, bannerPicUrl, imgSaveFullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
