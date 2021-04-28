package io.github.syske.dailynote.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: daily-note
 * @description:
 * @author: syske
 * @create: 2021-02-01 12:08
 */
public class DateUtil {
    /**
     * 获取传入日期的倒计时
     *
     * @param targetDateStr 目标日期，格式 yyyy-MM-dd
     * @return 返回天数
     */
    public static int getCountDownDays(String targetDateStr, Date today) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateStr = format.format(today);  //第二个日期
        //算两个日期间隔多少天
        Date nowDate = format.parse(nowDateStr);
        Date targetDate = format.parse(targetDateStr); // 目标日期
        int days = (int) ((targetDate.getTime() - nowDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }
}
