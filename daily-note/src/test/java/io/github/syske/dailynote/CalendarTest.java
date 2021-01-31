package io.github.syske.dailynote;

import io.github.syske.dailynote.util.ChineseCalendar;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarTest {

    @Test
    public void calendarTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ChineseCalendar.Element element = ChineseCalendar.getCalendarDetail(dateFormat.parse("2020-12-22"));
        System.out.println(element);
    }

    @Test
    public void calTest() {

    }
}
