package com.blundell.tut;

public interface LedStrip {

    /**
     * Blinks the RGB led strip with the colors
     *
     * @param blinkRate how often to change color
     * @param colors    the colors to blink between
     */
    void colorBlink(long blinkRate, int... colors);

    void close();

}
