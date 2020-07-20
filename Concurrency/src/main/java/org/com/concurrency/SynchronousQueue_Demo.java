package org.com.concurrency;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SynchronousQueue helps us coordinate the sharing of information between threads,
 * so that we can be sure that a value has already been set before accessed.
 *
 * Best used in cases where there are always available consumers, ready to take the added element.
 */
public class SynchronousQueue_Demo {

    private final static Logger LOGGER = LoggerFactory.getLogger(SynchronousQueue_Demo.class);
    private final static SynchronousQueue<Integer> SYNCHRONOUS_QUEUE = new SynchronousQueue();

    public static void main(String[] args) throws InterruptedException {

        Integer randomInt = new Random().nextInt();

        LOGGER.info("Initial size of the queue: " + SYNCHRONOUS_QUEUE.size());

        Thread first = new Thread(
            // first runnable puts a number in the queue, while access from other threads is blocked
            () -> {
                try {
                    SYNCHRONOUS_QUEUE.put(randomInt);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            });

        Thread second = new Thread(
            // second runnable waits until the first has finished, then proceeds to take that number from the queue
            () -> {
                try {
                    int taken = SYNCHRONOUS_QUEUE.take();
                    LOGGER.info("Taking item from queue: " + taken);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            });

        first.start();
        second.start();

        Thread.sleep(1000);

        LOGGER.info("Both threads finished execution, size of the queue is back to: " + SYNCHRONOUS_QUEUE.size());
    }

}
