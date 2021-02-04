package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ImageTest {

    private ImageUtil cg = new ImageUtil();
    @Test
    public void imgTest() {

        try {
            String mainContent = "人不是从娘胎里出来就一成不变的，相反，生活会逼迫他一次又一次地脱胎换骨。" ;
            String bookTitle = "霍乱时期的爱情";
            String authorName = "加西亚·马尔克斯";
            StringBuilder authorInfo = new StringBuilder("—— ")
                    .append(authorName)
                    .append("《")
                    .append(bookTitle)
                    .append("》");
            String footerContent = "-【每日读书札记】-";
            String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
//            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
            //String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String uuidStr = UUIDUtil.getUUIDStr();
            String imgSaveFullPath = "D:\\"+ uuidStr +".jpg";
            String faceImgSaveFullPath = "D:\\face-img-"+ uuidStr +".jpg";
            String mainContImgPath = cg.getImageUrl();
//            String mainContImgPath = "https://gitee.com/sysker/picBed/raw/master/images/20210204091907.png";
            cg.createReadingNoteCard(qrCodeImgPath, imgSaveFullPath, mainContImgPath, mainContent, authorInfo.toString(), footerContent);
            cg.createFaceImg(bookTitle, mainContImgPath, faceImgSaveFullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFaceImg() throws IOException {
        String imgSaveFullPath = "D:\\face-img-"+ UUIDUtil.getUUIDStr() +".jpg";
//            String mainContImgPath = cg.getImageUrl();
        String mainContImgPath = "https://gitee.com/sysker/picBed/raw/master/images/20210204091907.png";
        String bookTitle = "身份的焦虑";
        cg.createFaceImg(bookTitle, mainContImgPath, imgSaveFullPath);
    }
}
