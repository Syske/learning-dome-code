package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    public void imgTest() {
        ImageUtil cg = new ImageUtil();
        try {
            String mainContent = "生长通常被看成是积极的，但是没有东西会永远生长。如果任何形式的生长不断向前发展，最终都会变成怪物或变得具有毁灭性。有衰退才会有新的成长。生长与衰老两者相互依赖。" ;
            String authorInfo = "—— 埃克哈特·托利《当下的力量》";
            String footerCcontent = "-【每日读书札记】-";
            String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
//            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
            //String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String imgSaveFullPath = "D:\\"+ UUIDUtil.getUUIDStr() +".jpg";
//            String mainContImgPath = cg.getImageUrl();
            String mainContImgPath = "https://gitee.com/sysker/picBed/raw/master/images/20210201095715.png";
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
