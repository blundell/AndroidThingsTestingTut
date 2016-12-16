package com.blundell.tut;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private HandlerThread pioThread;
    private Handler handler;
    private LedStrip ledStrip;
    private AnimateRunnable animateRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup our background threading mechanism
        // this is used to send commands to the peripherals
        pioThread = new HandlerThread("pioThread");
        handler = new Handler(pioThread.getLooper());
        pioThread.start();
        // instantiate a connection to our peripheral
        ledStrip = getLedStrip();
        animateRunnable = new AnimateRunnable(ledStrip, handler);
        // command the periphal to animate on a background thread
        handler.post(animateRunnable);
    }

    /**
     * As an example in this tutorial, you can toggle peripheral implementations with flavors,
     * could also be done at runtime with shared preferences as an example
     */
    @NonNull
    private LedStrip getLedStrip() {
        if (BuildConfig.MOCK_MODE) {
            return new MockLedStrip();
        } else {
            return new Apa101LedStrip();
        }
    }

    /**
     * this is created for the sake of the tutorial
     * usually you would have more business logic and some model classes
     * these model classes are where your testing would take place
     * but for the sake of the tutorial AndroidRunnableTest.java was made
     */
    public static class AnimateRunnable implements Runnable {

        private final LedStrip ledStrip;
        private final Handler handler;

        AnimateRunnable(LedStrip ledStrip, Handler handler) {
            this.ledStrip = ledStrip;
            this.handler = handler;
        }

        @Override
        public void run() {
            long blinkRate = TimeUnit.SECONDS.toSeconds(1);
            ledStrip.colorBlink(blinkRate, Color.RED, Color.GREEN);
            handler.post(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(animateRunnable);
        pioThread.quitSafely();
        ledStrip.close();
    }

}
