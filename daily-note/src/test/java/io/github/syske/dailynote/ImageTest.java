package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    public void imgTest() {
        ImageUtil cg = new ImageUtil();
        try {
            String mainContent = "所谓“成长”这件事，说穿了，就是一个接受不那么讨人喜欢的真相的过程。其中一个真相就是：你必然生活在一个智力和体能都参差不平衡的环境中。一些人比你弱，其他的人比你强。" ;
            String authorInfo = "—— 蒋方舟《我承认我不曾历经沧桑》";
            String footerCcontent = "-【每日读书札记】-";
            String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
//            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
            //String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String imgSaveFullPath = "D:\\"+ UUIDUtil.getUUIDStr() +".jpg";
//            String mainContImgPath = cg.getImageUrl();
            String mainContImgPath = "https://gitee.com/sysker/picBed/raw/master/20210131155932.jpg";
            cg.createReadingNoteCard(qrCodeImgPath, imgSaveFullPath, mainContImgPath, mainContent, authorInfo, footerCcontent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testInt() {
        int a = 11;
        int b = 6;
        int c = a/b;
        System.out.println(c);
    }
}
