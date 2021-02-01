package io.github.syske.dailynote;

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
        ChineseCalendar.Element element = ChineseCalendar.getCalendarDetail(dateFormat.parse("2021-02-12"));
        System.out.println(element);
    }

    @Test
    public void calTest() throws ParseException {
        int days = DateUtil.getCountDownDays(2021, 3, 12);
        System.out.println(days);
    }
}
