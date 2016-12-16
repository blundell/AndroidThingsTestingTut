package com.blundell.tut;

import android.util.Log;

class MockLedStrip implements LedStrip {

    private static final String TAG = MockLedStrip.class.getSimpleName();

    @Override
    public void colorBlink(long blinkRate, int... colors) {
        Log.d(TAG, "Blink the led strip at rate: " + blinkRate);
        for (int color : colors) {
            Log.d(TAG, "Blink the led strip color: " + color);
        }
    }

    @Override
    public void close() {
        Log.d(TAG, "close the led strip IO");
    }
}
