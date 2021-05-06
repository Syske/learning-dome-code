package io.github.syske.springbootjwtdemo.sevice;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-13 23:55
 */
public class DataSource {
    public static Map<String, Map<String, String>> data;

    static {

        Map<String, String> detail = new HashMap<>();
        detail.put("password", "123456");
        detail.put("role", "admin");
        data.put("admin", detail);
    }


    public static Map<String, Map<String, String>> getData() {
        return data;
    }

    public static void setData(Map<String, Map<String, String>> data) {
        DataSource.data = data;
    }
}
