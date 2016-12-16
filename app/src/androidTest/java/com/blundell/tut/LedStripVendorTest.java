package com.blundell.tut;

import android.graphics.Color;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

/**
 * For our example the assertion has to be manual
 * Therefore these tests are good to run when when you first
 * learn a piece of hardware or you change to a new manufacturer
 * to ensure you have the same contracted behaviour
 */
public class LedStripVendorTest {

    @Rule
    public ActivityTestRule rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ledsBlinkSingleColor() throws Exception {
        LedStrip ledStrip = new Apa101LedStrip();

        ledStrip.colorBlink(2, Color.RED);

        // manual assertion
    }

    @Test
    public void ledsBlinkAlternateColors() throws Exception {
        LedStrip ledStrip = new Apa101LedStrip();

        ledStrip.colorBlink(2, Color.RED, Color.YELLOW);

        // manual assertion
    }
}
