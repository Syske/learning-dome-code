package io.github.syske.dailynote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.syske.dailynote.util.HttpClientUtil;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class HttpTest {
    @Test
    public void getImage() throws Exception {
        HttpClientUtil httpClientUtil = HttpClientUtil.init();
        String resurt = httpClientUtil.doPost("https://api.ixiaowai.cn/gqapi/gqapi.php?return=json", "");
        JSONObject jsonObject = JSON.parseObject(resurt);
        System.out.println(resurt);
        System.out.println(jsonObject.get("imgurl"));
    }

    @Test
    public void getWeatherInfo() throws Exception {
        HttpClientUtil httpClientUtil = HttpClientUtil.init();
        String resurt = httpClientUtil.doGet("http://www.weather.com.cn/data/cityinfo/101110101.html", "");
        JSONObject jsonObject = JSON.parseObject(resurt);
        System.out.println(jsonObject);
    }

    @Test
    public void getWeatherInfo2() throws Exception {
        HttpClientUtil httpClientUtil = HttpClientUtil.init();
        String resurt = httpClientUtil.doPost("https://l-by.cn/api/gqapi/gqapi.php?return=json", "");
        JSONObject jsonObject = JSON.parseObject(resurt);
        System.out.println(jsonObject);
    }
}
