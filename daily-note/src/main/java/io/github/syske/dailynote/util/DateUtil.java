package io.github.syske.dailynote.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        //第二个日期
        String nowDateStr = format.format(today);
        //算两个日期间隔多少天
        Date nowDate = format.parse(nowDateStr);
        // 目标日期
        Date targetDate = format.parse(targetDateStr);
        int days = (int) ((targetDate.getTime() - nowDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 获取当前时间，时间格式：yyyy-MM-dd
     * @return
     */
    public static String getDatestrYYYY_MM_dd() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
