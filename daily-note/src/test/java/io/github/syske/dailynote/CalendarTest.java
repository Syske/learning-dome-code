package io.github.syske.dailynote;

import com.nlf.calendar.Lunar;
import io.github.syske.dailynote.util.ChineseCalendar;
import io.github.syske.dailynote.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarTest {

    @Test
    public void calendarTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ChineseCalendar.Element element = ChineseCalendar.getCalendarDetail(dateFormat.parse("2021-02-03"));
        System.out.println(element);
    }

    @Test
    public void calTest() throws ParseException {
//        int days = DateUtil.getCountDownDays(2021, 3, 12);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = format.format(new Date());  //第二个日期

        String dbtime2 = "2021-02-12";  //第一个日期
//算两个日期间隔多少天
        Date date1 = format.parse(nowDate);
        Date date2 = format.parse(dbtime2);
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        System.out.println(days);
    }

    @Test
    public void testCander() {
        //今天
        Lunar date = new Lunar();

        //指定阴历的某一天
//        Lunar date = new Lunar(1986,4,21);
        System.out.println(date.toFullString());
        System.out.println(date.getSolar().toFullString());
    }
}
