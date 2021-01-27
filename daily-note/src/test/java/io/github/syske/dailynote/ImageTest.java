package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    public void imgTest() {
        ImageUtil cg = new ImageUtil();
        try {
            String mainContent = "生命短促，没有时间可以再浪费，一切随心自由才是应该努力去追求的，别人如何想我便是那么的无足轻重了。" ;
            String authorInfo = "—— 三毛《三毛全集》";
            String footerCcontent = "-【每日读书札记】-";
            String qrCodeImgPath = "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
//            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
            //String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String imgSaveFullPath = "D:\\"+ UUIDUtil.getUUIDStr() +".jpg";
            String mainContImgPath = cg.getImageUrl();
//            String mainContImgPath = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwx3.sinaimg.cn%2Flarge%2Fbd478545ly1g5wr567rjjj20rs0fmncg.jpg&refer=http%3A%2F%2Fwx3.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614269386&t=188264014ca1389719f026451046f818";
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
