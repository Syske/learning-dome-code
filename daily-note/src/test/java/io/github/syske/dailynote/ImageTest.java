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
            "你内心肯定有着某种火焰，能把你和其他人区别开来。";
        String bookTitle = "平凡之路";
        String authorName = "朴树";
        String bannerPicUrl = "https://images.unsplash.com/photo-1466120262033-5fb3f4f67132?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1662&q=80";
        NoteBookInfo noteBookInfo = new NoteBookInfo();
        noteBookInfo.setBookTitle(bookTitle).setAuthor(authorName).setNoteContent(mainContent)
            .setBannerPicUrl(bannerPicUrl);
        imageService.generateDailyNoteCard(noteBookInfo);
    }

    @Test
    public void createPic() {
        String title = "wsl搭建k8s集群补充内容";
        String bannerPicUrl = "https://images.unsplash.com/photo-1637348982424-d8d179203cf2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1229&q=80";

        imageService.generateBannerPicWithImage(title, bannerPicUrl, 1200, 300);
    }

    @Test
    public void batchCreatePic() {

        String bannerPicUrl0 = "https://images.unsplash.com/photo-1510832198440-a52376950479?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1167&q=80";
        String bannerPicUrl1 = "https://images.unsplash.com/photo-1581687004185-f912200c4a31?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
        String bannerPicUrl2 = "https://images.unsplash.com/photo-1503342521957-a6c73074d732?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1171&q=80";
        String bannerPicUrl3 = "https://images.unsplash.com/photo-1581716664010-2ba06ce8f51a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1331&q=80";


        List<String> urlList = Lists.newArrayList(
                bannerPicUrl0,
                bannerPicUrl1,
                bannerPicUrl2,
                bannerPicUrl3
                );
        List<String> titleList = Lists.newArrayList(
                " 流式编程时代，效率之王了解下？",
                " 要想java学的好，optional少不了",
                "lambda表达式之forEach",
                "forEach、forItem和forI的性能测试"
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
