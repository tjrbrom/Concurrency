package org.com.concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * With RecursiveTask, the computation of each task is bound to return a value
 * that can be united into a single result.
 * <p>
 * The invokeAll() method puts tasks in a common pool and returns Futures.
 * The primary method for awaiting completion and extracting
 * results of a task is {@link #join}
 */
public class RecursiveTaskImpl extends RecursiveTask<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(RecursiveTaskImpl.class);
    private static final AtomicInteger TOTAL = new AtomicInteger(0);
    int[] ints;

    public RecursiveTaskImpl(int[] ints) {
        this.ints = ints;
    }

    public static void main(String[] args) {

        int[] testArr = {4, 2, 1, 4, 2, 3, 5, 9, 8, 3, 5, 11, 8};

        new RecursiveTaskImpl(testArr).compute();

        LOGGER.info(String.valueOf(TOTAL));
    }

    @Override
    protected Integer compute() {
        // say we want to split the array processing, if it's too big...
        if (ints.length > 10) {
            return ForkJoinTask.invokeAll(splitArr())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        }
        // if the array is small, don't bother dividing...
        else {
            return print(ints);
        }
    }

    // divide the collection into smaller ones, based on some logic
    // return a collection of tasks that will be invoked by the fork/join framework
    public Collection<RecursiveTaskImpl> splitArr() {

        List<RecursiveTaskImpl> splitted = new ArrayList<>();

        splitted.add(new RecursiveTaskImpl(
                Arrays.copyOfRange(
                        ints, 0, ints.length / 2
                )
        ));
        splitted.add(new RecursiveTaskImpl(
                Arrays.copyOfRange(
                        ints, ints.length / 2, ints.length
                )
        ));
        return splitted;
    }

    private Integer print(int[] ints) {
        return TOTAL.addAndGet(Arrays
                .stream(ints)
                .sum());
    }

}
