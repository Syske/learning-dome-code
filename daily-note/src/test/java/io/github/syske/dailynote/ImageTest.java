package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;

public class ImageTest {

    private ImageUtil cg = new ImageUtil();

    @Test
    public void imgTest() {

        try {
            String mainContent = "当自律变成一种本能的习惯，你就会享受到它的快乐。";
            String bookTitle = "";
            String authorName = "村上春树";
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
            String mainContImgPath = "https://pics2.baidu.com/feed/1ad5ad6eddc451dab4a2d4b6e65b3460d1163247.jpeg?token=8d290b20f0accdd037f0827a323998ba";
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
        cg.createFaceImg(bookTitle, imgSaveFullPath);
    }
}
