package io.github.syske.dailynote;

import java.io.File;
import java.util.Date;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.syske.dailynote.service.ImageService;
import io.github.syske.dailynote.service.entity.BannerInfo;
import io.github.syske.dailynote.service.entity.NoteBookInfo;
import io.github.syske.dailynote.util.ChineseColorEnum;
import io.github.syske.dailynote.util.ImageUtil;
import io.github.syske.dailynote.util.UUIDUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageTest {

    private ImageUtil cg = new ImageUtil();

    @Autowired
    private ImageService imageService;

    @Test
    public void imgTest() {

        try {
            String mainContent =
                "我或许败北，或许迷失自己，或许哪里也抵达不了，或许我已失去一切，任凭怎么挣扎也只能徒呼奈何，或许我只是徒然掬一把废墟灰烬，唯我一人蒙在鼓里，或许这里没有任何人把赌注下在我身上。无所谓。有一点是明确的：至少我有值得等待有值得寻求的东西。";
            String bookTitle = "村上春树";
            String authorName = "奇鸟行状录";
            StringBuilder authorInfo = new StringBuilder("—— ").append(authorName);
            if (StringUtils.isNotBlank(bookTitle)) {
                authorInfo.append("《").append(bookTitle).append("》");
            }
            Date date = new Date();
            String footerContent = "-【每日读书札记】-";
            String qrCodeImgPath =
                "https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg";
            // String qrCodeImgPath = "D:\\Users\\Administrator\\Downloads\\qrcode_for_gh_6985fde6e5e8_258 (1).jpg";
            // String mainContImgPath = "D:\\Users\\Administrator\\Pictures\\Saved Pictures\\wallhaven-vgl8o8.jpg";
            String uuidStr = UUIDUtil.getUUIDStr();
            String imgSaveFullPath = "D:\\tmp\\img\\created\\" + uuidStr + ".jpg";
            String faceImgSaveFullPath = "D:\\tmp\\img\\created\\face-img-" + uuidStr + ".jpg";
            // String mainContImgPath = cg.getImageUrl();
            String mainContImgPath = "https://img0.baidu.com/it/u=2568490282,3538825398&fm=26&fmt=auto&gp=0.jpg";
            cg.createReadingNoteCard(qrCodeImgPath, imgSaveFullPath, mainContImgPath, mainContent,
                authorInfo.toString(), footerContent, date);
            cg.generateBannerPic(authorName, mainContImgPath, faceImgSaveFullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generateBnnerPicTest() {
        String bookTitle = "Example Everyday";
        BannerInfo bannerInfo = new BannerInfo();
        bannerInfo.setTitle(bookTitle).setBackgroundColorEnum(ChineseColorEnum.KONG_QUE_LAN);
        imageService.generateBannerPic(bannerInfo);
    }

    @Test
    public void testGenerateDailyNotePic() {
        String mainContent =
            "不管全世界所有人怎么说，我都认为自己的感受才是最真实的。无论别人怎么看，我都尽可能不打乱自己的节奏。喜欢的事自然可以坚持，不喜欢怎么也长久不了。";
        String bookTitle = "";
        String authorName = "佚名";
        String bannerPicUrl = "https://img2.baidu.com/it/u=2627620227,1186363145&fm=26&fmt=auto&gp=0.jpg";
        NoteBookInfo noteBookInfo = new NoteBookInfo();
        noteBookInfo.setBookTitle(bookTitle).setAuthor(authorName).setNoteContent(mainContent)
            .setBannerPicUrl(bannerPicUrl);
        imageService.generateDailyNoteCard(noteBookInfo);
    }

    @Test
    public void colorTest() {
        try {
            String utf8String = FileUtil.readUtf8String("./colors.json");
            JSONArray jsonArray = JSON.parseArray(utf8String);
            StringBuilder colorBuilder = new StringBuilder();
            jsonArray.forEach( json -> {
                JSONObject color = (JSONObject) json;
                System.out.println(color.get("CMYK"));
                System.out.println(color.get("pinyin"));
                System.out.println(color.get("hex"));
                JSONArray rgb = (JSONArray)color.get("RGB");
                System.out.println(rgb);
                System.out.println(color.get("name"));
                colorBuilder.append("\n").append("/**\n")
                        .append("* ").append(color.get("name")).append("\n")
                        .append("*/\n");
                colorBuilder.append(((String)color.get("pinyin")).toUpperCase())
                        .append("(").append("new Color(")
                        .append(rgb.get(0)).append(",").append(rgb.get(1)).append(",")
                        .append(rgb.get(2))
                        .append("), ").append("\"").append(color.get("name")).append("\", \"")
                .append(color.get("name")).append("色\"),\n");

            });
            FileUtil.writeUtf8String(colorBuilder.toString(), new File("./colorEnum.java"));
            System.out.println(colorBuilder);
        } catch (Exception e) {

        }
    }

    @Test
    public void testFileExtSubString() {
        String uuidStr = UUIDUtil.getUUIDStr();
        String imgSaveFullPath = "D:\\tmp\\img\\created\\" + uuidStr + ".jpg";
        System.out.println(imgSaveFullPath.substring(imgSaveFullPath.lastIndexOf('.') + 1));
    }
}
