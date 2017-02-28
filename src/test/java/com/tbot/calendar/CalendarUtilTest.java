package com.tbot.calendar;

import org.joda.time.LocalDate;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

import static org.junit.Assert.*;

public class CalendarUtilTest {

    CalendarUtil calendarUtil = new CalendarUtil();

    @Test
    public void testGenerateKeyboardNull() throws Exception {
        assertNull(calendarUtil.generateKeyboard(null));
    }

    @Test
    public void testGenerateKeyboard() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MARCH, 10);
        String keyboard = calendarUtil.generateKeyboard(LocalDate.fromCalendarFields(calendar));
        assertNotNull(keyboard);
        assertTrue(keyboard.length() > 0);
        URL url = getClass().getClassLoader().getResource("march2017.json");
        assertNotNull(url);
        String march = readFile(url.getPath(), StandardCharsets.UTF_8);
        assertEquals(march, keyboard);
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}