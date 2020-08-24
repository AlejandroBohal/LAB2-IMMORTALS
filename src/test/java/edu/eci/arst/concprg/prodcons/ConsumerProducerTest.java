package edu.eci.arst.concprg.prodcons;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsumerProducerTest {
    Queue<Integer> queue1;
    Queue<Integer> queue2;

    @Before
    public void setup(){
    }
    @Test
    public void shouldStartProduction() throws InterruptedException {
        queue1 = new LinkedBlockingQueue<>();
        Producer producer = new Producer(queue1, 15);
        producer.start();
        Thread.sleep(500);
        System.out.println("");
        Assert.assertEquals(15, queue1.size());
    }

    @Test
    public void shouldConsumeQueue() throws InterruptedException {
        queue2 = new LinkedBlockingQueue<>();
        queue2.add(33);
        queue2.add(34);
        queue2.add(35);
        queue2.add(36);
        queue2.add(37);
        queue2.add(39);
        Consumer consumer = new Consumer(queue2);
        consumer.start();
        Thread.sleep(3000);
        Assert.assertEquals(0, queue2.size());
    }
}
