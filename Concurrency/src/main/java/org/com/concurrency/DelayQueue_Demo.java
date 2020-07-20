package org.com.concurrency;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An unbounded blocking queue of Delayed elements, in which an element can only be taken when its delay has expired
 */
public class DelayQueue_Demo {

    private final static Logger LOGGER = LoggerFactory.getLogger(DelayQueue_Demo.class);

    public static void main(String args[]) throws InterruptedException {

        DelayQueue<DelayTest> delayQueue = new DelayQueue();

        DelayTest dt1 = new DelayTest("first", 10);
        DelayTest dt2 = new DelayTest("second", 5);
        DelayTest dt3 = new DelayTest("third", 15);

        delayQueue.offer(dt1);
        delayQueue.offer(dt2);
        delayQueue.offer(dt3);

        // after five seconds have passed, the second object will be the first to come out,
        // because this queue sorts it's contents based on their configured delay
        LOGGER.info(delayQueue.take().getName());
        LOGGER.info(delayQueue.take().getName());
        LOGGER.info(delayQueue.take().getName());
    }

}

class DelayTest implements Delayed {

    private long delay;
    private String name;

    DelayTest(String name, long delay) {
        this.delay = System.currentTimeMillis() + delay;
        this.name = name;
    }

    String getName() {
        return name;
    }

    @Override
    public long getDelay(TimeUnit unit) {

        return unit.convert(delay - System.currentTimeMillis(), TimeUnit.SECONDS);
    }

    @Override
    public int compareTo(Delayed other) {

          return this.delay < ((DelayTest) other).delay ? -1 : (this.delay > ((DelayTest) other).delay ? 1 : 0);
    }

}
