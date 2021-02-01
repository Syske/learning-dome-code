package io.github.syske.dailynote.util;

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
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static int getCountDownDays (int year, int month, int dayOfMonth) {
        Date today = new Date();
        Calendar targetDay = Calendar.getInstance();
        targetDay.set(Calendar.YEAR, year);
        targetDay.set(Calendar.MONTH, month);
        targetDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        long targetDayTimeInMillis = targetDay.getTime().getTime();
        long todayTimeInMillis = today.getTime();
        long dd = targetDayTimeInMillis - todayTimeInMillis;
        int days = (int)dd /( 1000 * 3600 * 24);
        return days;
    }
}
