package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ChineseCalendar;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarTest {

    @Test
    public void calendarTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        ChineseCalendar.Element element = ChineseCalendar.getCalendarDetail(dateFormat.parse("2021-01-20"));
        System.out.println(element);
    }
}
