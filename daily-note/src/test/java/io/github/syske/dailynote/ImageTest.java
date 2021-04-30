package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ChineseColorEnum;
import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.awt.*;
import java.io.IOException;

public class ImageTest {

    private ImageUtil cg = new ImageUtil();

    @Test
    public void imgTest() {

        try {
            String mainContent = "日出未必意味着光明，太阳也无非是颗辰星而已，只有在我们醒着时，才是真正的破晓。";
            String bookTitle = "瓦尔登湖";
            String authorName = "梭罗";
            StringBuilder authorInfo = new StringBuilder("—— ")
                    .append(authorName);
            if (StringUtils.isNotBlank(bookTitle)) {
                authorInfo.append("《")
                        .append(bookTitle)
                        .append("》");
            }

            String footerContent = "-【每日读书札记】-";
            String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
//            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
//            String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String uuidStr = UUIDUtil.getUUIDStr();
            String imgSaveFullPath = "D:\\tmp\\img\\created\\" + uuidStr + ".jpg";
            String faceImgSaveFullPath = "D:\\tmp\\img\\created\\face-img-" + uuidStr + ".jpg";
//            String mainContImgPath = cg.getImageUrl();
            String mainContImgPath = "https://img1.baidu.com/it/u=956399857,3336931491&fm=26&fmt=auto&gp=0.jpg";
            cg.createReadingNoteCard(qrCodeImgPath, imgSaveFullPath, mainContImgPath, mainContent, authorInfo.toString(), footerContent);
            cg.createFaceImg(authorName, mainContImgPath, faceImgSaveFullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFaceImg() throws IOException {
        String imgSaveFullPath = "D:\\face-img-" + UUIDUtil.getUUIDStr() + ".jpg";
//            String mainContImgPath = cg.getImageUrl();
        String mainContImgPath = "https://gitee.com/sysker/picBed/raw/master/images/20210204091907.png";
        String bookTitle = "身份的焦虑";
        cg.createFaceImg(bookTitle, mainContImgPath, imgSaveFullPath);
    }

    @Test
    public void testFaceImg2() throws IOException {
        String imgSaveFullPath = "D:\\face-img-" + UUIDUtil.getUUIDStr() + ".jpg";
//            String mainContImgPath = cg.getImageUrl();
        String mainContImgPath = "https://gitee.com/sysker/picBed/raw/master/images/20210204091907.png";
        String bookTitle = "Example Everyday";
        Color backgroundColor = ChineseColorEnum.E_ZHANG_HUANG.getColor();
        Color fontColor = null;
        if (ChineseColorEnum.isDark(backgroundColor)) {
            fontColor = new Color(255, 255, 255);
        } else {
            fontColor = new Color(0, 0, 0);
        }
        cg.createFaceImg(bookTitle, backgroundColor,
                fontColor, imgSaveFullPath);
    }
}
