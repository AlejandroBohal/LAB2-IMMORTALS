package edu.eci.arsw.highlandersim;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class HighLanderTest {
    CopyOnWriteArrayList<Immortal> immortals1, immortals2;

    @Before
    public void preparar() {
        immortals1 = new CopyOnWriteArrayList<>();
        immortals2 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 7; i++) {
            immortals1.add(new Immortal("im" + i,  i, immortals1, new AtomicInteger(100), 10,null));
        }
        for (int i = 0; i < 7; i++) {
            immortals2.add(new Immortal("im" + i,  i, immortals2, new AtomicInteger(100), 10,null));
        }
        for (Immortal i : immortals1) {
            i.start();
        }
        for (Immortal i : immortals2) {
            i.start();
        }
    }

    @Test
    public void shouldStop() throws InterruptedException {
        for (Immortal i : immortals1) {
            i.setRunning(false);
        }
        Thread.sleep(5000);
        for (Immortal i : immortals1) {
            if (i.getState().toString().equals("TIMED_WAITING") || i.getState().toString().equals("WAITING")) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        }
    }

    @Test
    public void shouldKill() throws InterruptedException {
        for (Immortal immortal : immortals2) {
            immortal.kill();
        }
        Thread.sleep(5000);
        for (Immortal i : immortals2) {
            if (i.getState().toString().equals("TERMINATED")) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        }
    }
}