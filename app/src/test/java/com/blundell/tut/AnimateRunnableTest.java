package com.blundell.tut;

import android.graphics.Color;
import android.os.Handler;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AnimateRunnableTest {

    @Test
    public void whenRunning_thenColorStripShouldBlink() throws Exception {
        LedStrip mockStrip = mock(LedStrip.class);
        Handler mockHandler = mock(Handler.class);
        MainActivity.AnimateRunnable animate = new MainActivity.AnimateRunnable(mockStrip, mockHandler);

        animate.run();

        verify(mockStrip).colorBlink(1L, Color.RED, Color.GREEN);
    }

    @Test
    public void whenRunning_thenShouldRepeatWhenComplete() throws Exception {
        LedStrip mockStrip = mock(LedStrip.class);
        Handler mockHandler = mock(Handler.class);
        MainActivity.AnimateRunnable animate = new MainActivity.AnimateRunnable(mockStrip, mockHandler);

        animate.run();

        verify(mockHandler).post(animate);
    }
}
