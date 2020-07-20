package org.com.concurrency;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This example shows the difference when using a simple HashMap, as opposed to using a ConcurrentHashMap
 * to achieve synchronization between threaded execution of operations on a Map.
 */
public class ConcurrentHashMap_Demo {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConcurrentHashMap_Demo.class);

    static void simpleHashMap_cannotHandleMultithreadedOperationsSafely() throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(4);

        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("random", 0);

        for (int i = 0; i < 15; i++) {
            service.execute(() ->
                {
                    for (int j = 0; j < 15; j++) {
                        hashMap.computeIfPresent(
                                "random",
                                (key, value) -> value + 1
                        );
                    }
                }
            );
        }

        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);

        LOGGER.info("Result using simple HashMap and unsafe increments: " + hashMap.get("random"));
    }

    // this one works fine when using an AtomicInteger to increment the simple HashMap value !
    static void simpleHashMap_worksFineWithAtomicIncrement() throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(4);

        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("random", 0);

        AtomicInteger atomicInteger = new AtomicInteger(0);

        for (int i = 0; i < 15; i++) {
            service.execute(() ->
                {
                    for (int j = 0; j < 15; j++) {
                        hashMap.computeIfPresent(
                                "random",
                                (key, value) -> atomicInteger.incrementAndGet()
                        );
                    }
                }
            );
        }

        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);

        LOGGER.info("Result using simple HashMap and safe/atomic increments: " + hashMap.get("random"));
    }

    // Lastly, we can use a ConcurrentHashMap to achieve automatic sinchronization
    static void concurrentHashMap() throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(4);

        Map<String, Integer> hashMap = new ConcurrentHashMap<>();
        hashMap.put("random", 0);

        for (int i = 0; i < 15; i++) {
            service.execute(() ->
                {
                    for (int j = 0; j < 15; j++) {
                        hashMap.computeIfPresent(
                                "random",
                                (key, value) -> value + 1
                        );
                    }
                }
            );
        }

        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);

        LOGGER.info("Result using ConcurrentHashMap: " + hashMap.get("random"));
    }

    public static void main(String[] args) throws InterruptedException {

        simpleHashMap_cannotHandleMultithreadedOperationsSafely();
        simpleHashMap_worksFineWithAtomicIncrement();
        concurrentHashMap();

    }

}
