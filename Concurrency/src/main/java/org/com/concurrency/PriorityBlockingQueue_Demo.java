package org.com.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PriorityBlockingQueue is a blocking queue that defines a priority with which elements are put and polled from,
 * using Comparator/Comparable implementation of corresponding object type.
 *
 * When we poll for an element, the highest priority one will be selected.
 */
public class PriorityBlockingQueue_Demo {

    private final static Logger LOGGER = LoggerFactory.getLogger(PriorityBlockingQueue_Demo.class);

    private final static PriorityBlockingQueue<Integer> QUEUE = new PriorityBlockingQueue<>();

    public static void main(String args[]) throws InterruptedException {

        // these numbers will be ordered when added to the queue
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(5);
        list.add(2);
        list.add(4);

        new Thread(() -> {

            String threadName = Thread.currentThread().getName();

            LOGGER.info(threadName + " tries to take, but is blocked until elements are added to the queue. Waiting...");

            while (true) {

                try {
                    int taken = QUEUE.take();
                    LOGGER.info(threadName + " took the number " + taken + " from the queue");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if(QUEUE.isEmpty()) {
                    LOGGER.info(threadName + " received each number ordered by the priority queue");
                    return;
                }
            }

        }).start();

        Thread.sleep(3000);

        LOGGER.info("Adding a list of numbers to the queue");
        // the numbers are automatically ordered by the queue
        QUEUE.addAll(list);
    }

}
