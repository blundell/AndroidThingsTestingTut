package com.blundell.tut;

import android.graphics.Color;
import android.util.Log;

import com.google.android.things.contrib.driver.apa102.Apa102;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Apa102LedStrip implements LedStrip {

    private static final String TAG = Apa102LedStrip.class.getSimpleName();

    // LED configuration
    private static final int NUM_LEDS = 7;
    private static final int LED_BRIGHTNESS = 12; // 0 ... 31
    private static final Apa102.Mode LED_MODE = Apa102.Mode.BGR;

    private final Apa102 driver;

    private int[] ledColors = new int[NUM_LEDS];

    public Apa102LedStrip() {
        try {
            driver = new Apa102(BoardDefaults.getSPIPort(), LED_MODE);
            driver.setBrightness(LED_BRIGHTNESS);
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialise the LED driver", e);
        }
    }

    /**
     * Blinks the RGB led strip with the colors.
     * <p>
     * Assumes to be called on background thread
     */
    @Override
    public void colorBlink(long blinkRate, int... colors) {
        try {
            clear(ledColors);
            driver.write(ledColors);
            TimeUnit.SECONDS.sleep(blinkRate);

            for (int color : colors) {
                setAllToColor(ledColors, color);
                driver.write(ledColors);
                TimeUnit.SECONDS.sleep(blinkRate);

                clear(ledColors);
                driver.write(ledColors);
            }

        } catch (IOException | InterruptedException e) {
            Log.e(TAG, "Error while blinking LEDs", e);
        }
    }

    private static void clear(int[] colorArray) {
        setAllToColor(colorArray, Color.BLACK);
    }

    /**
     * Sets all the elements in the color array to a given {@link Color}.
     *
     * @param colorArray array to be set
     * @param color      an integer representing an ARGB 32-bits color point
     */
    private static void setAllToColor(int[] colorArray, int color) {
        for (int i = 0; i < colorArray.length; i++) {
            colorArray[i] = color;
        }
    }

    @Override
    public void close() {
        try {
            driver.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception closing LED driver", e);
        }
    }
}
