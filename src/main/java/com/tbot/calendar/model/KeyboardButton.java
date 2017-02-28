package com.tbot.calendar.model;

/**
 * Model to generate keyboard
 */
public class KeyboardButton {

    private String text;
    private String callbackData;

    public KeyboardButton setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    public KeyboardButton setCallbackData(String callbackData) {
        this.callbackData = callbackData;
        return this;
    }

    public String getCallbackData() {
        return callbackData;
    }

}
