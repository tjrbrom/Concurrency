package org.com.concurrency;

import java.util.concurrent.ConcurrentLinkedDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConcurrentLinkedDeque is a thread-safe implementation of the Deque interface.
 *
 * Since it is lock-free by design, it avoids potential deadlock issues.
 */
public class ConcurrentLinkedDeque_Demo {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConcurrentLinkedDeque_Demo.class);

    public static void main(String[] args) {

        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();

        deque.addFirst(5);
        deque.addFirst(2);
        deque.addFirst(7);
        deque.addFirst(1);

        LOGGER.info("ConcurrentLinkedDeque: "
                + deque);

        LOGGER.info("Returning the Last element, using getLast(): "
                + deque.getLast());

        LOGGER.info("Checking the first element, using peekFirst(): "
                + deque.peekFirst());

        LOGGER.info("Removing the last element, using removeLast(): " + deque.removeLast());

        LOGGER.info("ConcurrentLinkedDeque: "
                + deque);
    }

}
