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
            String mainContent = "当错事有利可图时，做正确之事不易；当代价太高时，做正确之事很难；当不被理解时，做正确之事更难。" ;
            String bookTitle = "世界上到处都是有才华的穷人";
            String authorName = "";
            StringBuilder authorInfo = new StringBuilder("— ")
                    .append(authorName)
                    .append("《")
                    .append(bookTitle)
                    .append("》");
            String footerContent = "-【每日读书札记】-";
            String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
//            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
//            String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String uuidStr = UUIDUtil.getUUIDStr();
            String imgSaveFullPath = "D:\\images\\"+ uuidStr +".jpg";
            String faceImgSaveFullPath = "D:\\images\\face-img-"+ uuidStr +".jpg";
//            String mainContImgPath = cg.getImageUrl();
            String mainContImgPath = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fphoto.tuchong.com%2F1570057%2Ff%2F539885804.jpg&refer=http%3A%2F%2Fphoto.tuchong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1619015794&t=f38af37159d3382bdfafc72e118d6a0e";
//            String mainContImgPath = "http://localhost/images/wallhaven-vg7lv3.jpg";
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
