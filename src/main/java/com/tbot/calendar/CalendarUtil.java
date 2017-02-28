package com.tbot.calendar;

import com.google.gson.Gson;
import com.tbot.calendar.model.KeyboardButton;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CalendarUtil {

    public static final String IGNORE = "ignore!@#$%^&";

    public static final String[] WD = {"M", "T", "W", "T", "F", "S", "S"};

    public String generateKeyboard(LocalDate date) {

        if (date == null) {
            return null;
        }

        List<List<KeyboardButton>> keyboard = new ArrayList<>();

        // row - Month and Year
        List<KeyboardButton> headerRow = new ArrayList<>();
        headerRow.add(createButton(IGNORE, new SimpleDateFormat("MMM yyyy").format(date.toDate())));
        keyboard.add(headerRow);

        // row - Days of the week
        List<KeyboardButton> daysOfWeekRow = new ArrayList<>();
        for (String day : WD) {
            daysOfWeekRow.add(createButton(IGNORE, day));
        }
        keyboard.add(daysOfWeekRow);

        LocalDate firstDay = date.dayOfMonth().withMinimumValue();

        int shift = firstDay.dayOfWeek().get() - 1;
        int daysInMonth = firstDay.dayOfMonth().getMaximumValue();
        int rows = ((daysInMonth + shift) % 7 > 0 ? 1 : 0) + (daysInMonth + shift) / 7;
        for (int i = 0; i < rows; i++) {
            keyboard.add(buildRow(firstDay, shift));
            firstDay = firstDay.plusDays(7 - shift);
            shift = 0;
        }

        List<KeyboardButton> controlsRow = new ArrayList<>();
        controlsRow.add(createButton("<", "<"));
        controlsRow.add(createButton(">", ">"));
        keyboard.add(controlsRow);
        return new Gson().toJson(keyboard);
    }

    private KeyboardButton createButton(String callBack, String text) {
        return new KeyboardButton().setCallbackData(callBack).setText(text);
    }

    private List<KeyboardButton> buildRow(LocalDate date, int shift) {
        List<KeyboardButton> row = new ArrayList<>();
        int day = date.getDayOfMonth();
        LocalDate callbackDate = date;
        for (int j = 0; j < shift; j++) {
            row.add(createButton(IGNORE, " "));
        }
        for (int j = shift; j < 7; j++) {
            if (day <= (date.dayOfMonth().getMaximumValue())) {
                row.add(createButton(callbackDate.toString(), Integer.toString(day++)));
                callbackDate = callbackDate.plusDays(1);
            } else {
                row.add(createButton(IGNORE, " "));
            }
        }
        return row;
    }
}