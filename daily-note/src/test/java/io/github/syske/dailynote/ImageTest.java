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
            "生命的意义并不在于通过折腾、透支年轻的躯体，来寻得未来凭借物质、权利垒砌的安全感，而是洞悉世间路径，寻找到一条让内心踏实、宁静、格局广阔的路径，在路上与纯粹的自己重逢。";
        String bookTitle = "人生海海 素履之往";
        String authorName = "云鲸航";
        String bannerPicUrl = "https://images.unsplash.com/photo-1506886908870-1e5c3e685b3d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1700&q=80";
        NoteBookInfo noteBookInfo = new NoteBookInfo();
        noteBookInfo.setBookTitle(bookTitle).setAuthor(authorName).setNoteContent(mainContent)
            .setBannerPicUrl(bannerPicUrl);
        imageService.generateDailyNoteCard(noteBookInfo);
    }

    @Test
    public void batchCreatePic() {

        String bannerPicUrl0 = "https://images.unsplash.com/photo-1630590874752-61fbfc9ff5a6?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80";
        String bannerPicUrl1 = "https://images.unsplash.com/photo-1630524221837-33f97e89815d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80";
        String bannerPicUrl2 = "https://images.unsplash.com/photo-1429277005502-eed8e872fe52?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80";


        List<String> urlList = Lists.newArrayList(
                bannerPicUrl0,
                bannerPicUrl1,
                bannerPicUrl2
                );
        List<String> titleList = Lists.newArrayList(
                "spring-boot之webflux简单入门 - 上",
                "spring-boot之webflux简单入门 - 中",
                "spring-webflux真 · 流式编程"
                );

        for (int i = 0; i < urlList.size(); i++) {
            String bannerPicUrl = urlList.get(i);
            String title = titleList.get(i);
            imageService.generateBannerPicWithImage(title, bannerPicUrl, 1200, 500);
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
