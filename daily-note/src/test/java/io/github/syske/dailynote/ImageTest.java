package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.util.Date;

public class ImageTest {

    private ImageUtil cg = new ImageUtil();

    @Test
    public void imgTest() {

        try {
            String mainContent = "从某种意义上来看，世间一切，都是遇见。就像，冷遇见暖，就有了雨；春遇见冬，有了岁月；天遇见地，有了永恒；人遇见人，有了生命。";
            String bookTitle = "朗读者";
            String authorName = "董卿";
            StringBuilder authorInfo = new StringBuilder("—— ")
                    .append(authorName);
            if (StringUtils.isNotBlank(bookTitle)) {
                authorInfo.append("《")
                        .append(bookTitle)
                        .append("》");
            }
            Date date = new Date();
            String footerContent = "-【每日读书札记】-";
            String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
//            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
//            String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String uuidStr = UUIDUtil.getUUIDStr();
            String imgSaveFullPath = "D:\\tmp\\img\\created\\" + uuidStr + ".jpg";
            String faceImgSaveFullPath = "D:\\tmp\\img\\created\\face-img-" + uuidStr + ".jpg";
//            String mainContImgPath = cg.getImageUrl();
            String mainContImgPath = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2F84000.jiuchisu.com%2Farticle%2F2017%2F04%2F12017913470913.jpg&refer=http%3A%2F%2F84000.jiuchisu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622421754&t=330af2594b0dfe4d536816ca4541a113";
            cg.createReadingNoteCard(qrCodeImgPath, imgSaveFullPath, mainContImgPath, mainContent, authorInfo.toString(), footerContent, date);
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
