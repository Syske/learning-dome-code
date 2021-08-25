package io.github.syske.dailynote;

import java.io.File;
import java.util.Date;
import java.util.List;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.assertj.core.util.Lists;
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
                "树不可长得太快。一年生当柴，三年五年生当桌椅，十年百年的才有可能成栋梁。故要养深积厚，等待时间。";
            String bookTitle = "";
            String authorName = "佚名";
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
        String bookTitle = "七夕快乐！";
        BannerInfo bannerInfo = new BannerInfo();
        bannerInfo.setTitle(bookTitle).setBackgroundColorEnum(ChineseColorEnum.YAN_ZHI_HONG);
        imageService.generateBannerPic(bannerInfo);
    }

    @Test
    public void testGenerateDailyNotePic() {
        String mainContent =
//            "你要去相信，时光且长，你终将长成自己想要的模样，拥抱独属于你的未来。";
//            "做你最愿意做的那件事，那才是你真正的天赋所在。";
//            "人生长短，弹指一瞬。日常的重复，容易让人产生错觉，会认为一切都来得及，会有时间开始做自己喜欢的事情。";
//            "不管幸与不幸，都不要为自己的人生设限，以免阻挡了生命的阳光。";
//            "陪伴是最好的爱，可以抵挡世间所有的坚硬，温暖生命所有的岁月。";
//            "趁着岁月静好，勇敢去爱。不要等到时机消逝，再为那份错过的爱而懊悔、哭泣。我们终将赴一场名为爱的宴会，哪怕最后只剩回忆。";
//            "有人总说：已经晚了。实际上，现在就是最好的时光。对于一个真正有所追求的人来说，生命的每个时期都是年轻的、及时的。";
//            "--我不知道怎样才能过得更好，但是我尽力让现在当下的自己完好无损。";
            "人世间摸爬滚打至今，我唯一愿意视为真理的，就只有这一句话。一切都会过去的。";
        String bookTitle = "人间失格";
        String authorName = "太宰治";
        String bannerPicUrl = "https://images.unsplash.com/photo-1530064161350-7824b8cdeee9?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80";
        NoteBookInfo noteBookInfo = new NoteBookInfo();
        noteBookInfo.setBookTitle(bookTitle).setAuthor(authorName).setNoteContent(mainContent)
            .setBannerPicUrl(bannerPicUrl);
        imageService.generateDailyNoteCard(noteBookInfo);
    }

    @Test
    public void batchCreatePic() {

        String bannerPicUrl0 = "https://images.unsplash.com/photo-1517260911058-0fcfd733702f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1413&q=80";
        String bannerPicUrl1 = "https://images.unsplash.com/photo-1473186578172-c141e6798cf4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=966&q=80";
        String bannerPicUrl2 = "https://images.unsplash.com/photo-1544298903-35eee5a95b4d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1395&q=80";
        String bannerPicUrl3 = "https://images.unsplash.com/photo-1570469104943-b3f9aa53f049?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=967&q=80";
        String bannerPicUrl4 = "https://images.unsplash.com/photo-1544933863-582aca697094?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=933&q=80";
        String bannerPicUrl5 = "https://images.unsplash.com/photo-1531219432768-9f540ce91ef3?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80";

        List<String> urlList = Lists.newArrayList(
                bannerPicUrl0,
                bannerPicUrl1,
                bannerPicUrl2,
                bannerPicUrl3,
                bannerPicUrl4,
                bannerPicUrl5
        );
        List<String> titleList = Lists.newArrayList(
                "趁热打铁，我们今天来手写一个RPC框架……",
                "基于redis实现rpc服务注册",
                "增加动态代理，实现真正意义上的rpc",
                "解决接口与动态代理绑定问题，实现rpc的最后一站",
                "整合zk作为注册中心，实现更合理的rpc服务注册",
                "手写rpc服务 | 优化zk注册工具类，完善注册机制"
                );

        for (int i = 0; i < urlList.size(); i++) {
            String bannerPicUrl = urlList.get(i);
            String title = titleList.get(i);
            imageService.generateBannerPicWithImage(title, bannerPicUrl, 1200, 300);
        }

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
