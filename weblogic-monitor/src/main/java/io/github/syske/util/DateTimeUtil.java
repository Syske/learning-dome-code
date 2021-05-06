package io.github.syske.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 * @program: weblogic-monitor
 * @description:
 * @author: syske
 * @create: 2020-01-21 14:23
 */
public class DateTimeUtil {

    private DateTimeUtil() {
    }

    /**
     * 获取当前时间
     *
     * @param format 格式
     * @return
     */
    public static String getCurrentDateTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 获取第futuredayCount天2后的时间
     *
     * @param fomat          格式
     * @param futuredayCount 天数
     * @return
     */
    public static String getFutureDateTime(String fomat, int futuredayCount) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fomat);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, futuredayCount);
        String futureDate = simpleDateFormat.format(calendar.getTime());

        return futureDate;
    }
}
