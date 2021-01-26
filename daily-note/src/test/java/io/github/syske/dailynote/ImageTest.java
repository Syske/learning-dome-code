package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    public void imgTest() {
        ImageUtil cg = new ImageUtil();
        try {
            String mainContent = "我们就像是一株蒲公英， 虽然总有一天会被风吹散， 但是我也祈祷着，能和你飞去同一片土地。";
            String authorInfo = "—— 北川理惠《三行情书 》";
            String footerCcontent = "-【每日读书札记】-";
            String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
            //String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String imgSaveFullPath = "D:\\"+ UUIDUtil.getUUIDStr() +".jpg";
//            String mainContImgPath = cg.getImageUrl();
            String mainContImgPath = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdik.img.kttpdq.com%2Fpic%2F145%2F100834%2Fc1b734df1003a15f_1366x768.jpg&refer=http%3A%2F%2Fdik.img.kttpdq.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614253224&t=8b8c8a60f0da856b6b0e96c8a8531343";
            cg.createReadingNoteCard(qrCodeImgPath, imgSaveFullPath, mainContImgPath, mainContent, authorInfo, footerCcontent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
