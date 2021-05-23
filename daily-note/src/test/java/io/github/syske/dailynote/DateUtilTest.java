package io.github.syske.dailynote;

import io.github.syske.dailynote.util.DateUtil;
import org.junit.Test;

/**
 * @program: daily-note
 * @description: 日期工具类测试
 * @author: syske
 * @date: 2021-05-03 18:32
 */
public class DateUtilTest {
    @Test
    public void getDateTest() {
        System.out.println(DateUtil.getDatestrYYYY_MM_dd());
    }
}
