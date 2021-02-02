package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    public void imgTest() {
        ImageUtil cg = new ImageUtil();
        try {
            String mainContent = "凡是有价值的东西都不受时间的限制。或者换句话说，一种事物包含的价值越多，它就越不容易随着时间而改变。世上的价值会随时间而变化，而惟有那些超越了时间限制的事物才具有绝对的价值。" ;
            String authorInfo = "—— [奥]奥托·魏宁格《性与性格》";
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
