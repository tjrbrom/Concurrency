package org.com.concurrency;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CountDownLatch is a concurrency mechanism to block a thread until other threads have finished running.
 *
 * This is the simplest CountDownLatch example.
 */
public class CountDownLatch_Demo {

    private final static Logger LOGGER = LoggerFactory.getLogger(CountDownLatch_Demo.class);

    static final int NUM_THREADS = 4;

    static CountDownLatch countDownLatch = new CountDownLatch(NUM_THREADS);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new TestRunnable());
            thread.start();
        }

        LOGGER.info("Calling countDownLatch.await(), which forces us to wait for the specified number of threads to finish...");
        // when the countdown reaches zero, await terminates
        countDownLatch.await();
        LOGGER.info("No more waiting... All threads finished executing!");

    }

    static class TestRunnable implements Runnable {

        @Override
        public void run() {

            LOGGER.info("Calling countDownLatch.countDown() inside run() of " + Thread.currentThread().getName());
            countDownLatch.countDown();
        }

    }

}
