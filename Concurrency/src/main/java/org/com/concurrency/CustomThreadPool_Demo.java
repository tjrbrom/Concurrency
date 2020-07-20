package org.com.concurrency;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class CustomThreadPool_Demo {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomThreadPool_Demo.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int first = 1;
        int last = 10;

        List<Integer> list = IntStream.rangeClosed(first, last)
            .boxed()
            .collect(Collectors.toList());

        LOGGER.info("Contents of list:");
        list.stream().forEach(System.out::println);

        LOGGER.info("=====================");

        // The parallelism number can be the number of cores
        ForkJoinPool customThreadPool = new ForkJoinPool(4);

        int result = customThreadPool.submit(
            () -> list.parallelStream()
                    .reduce(0, Integer::sum)
        ).get();

        LOGGER.info("Sum of contents, using a custom thread pool: " + result);
    }

}
