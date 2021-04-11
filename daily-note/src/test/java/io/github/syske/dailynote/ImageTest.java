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
            String mainContent = "一个真正的探索者，一个真正要有所发现的人，是不会接受什么学说的。但是，已经有所领悟的过来人却可以赞成任何学说，任何道路，任何目标，什么也不能把他与生活在永恒之中、呼吸着神的气息的千千万万人分开。" ;
            String bookTitle = "悉达多";
            String authorName = "黑塞";
            StringBuilder authorInfo = new StringBuilder("—— ")
                    .append(authorName)
                    .append("《")
                    .append(bookTitle)
                    .append("》");
            String footerContent = "-【每日读书札记】-";
            String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
//            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
//            String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String uuidStr = UUIDUtil.getUUIDStr();
            String imgSaveFullPath = "D:\\tmp\\img\\created\\"+ uuidStr +".jpg";
            String faceImgSaveFullPath = "D:\\tmp\\img\\created\\face-img-"+ uuidStr +".jpg";
//            String mainContImgPath = cg.getImageUrl();
            String mainContImgPath = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1792341402,1807382616&fm=26&gp=0.jpg";
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
