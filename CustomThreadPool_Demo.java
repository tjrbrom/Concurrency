import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class CustomThreadPool_Demo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int first = 1;
        int last = 10;

        List<Integer> list = IntStream.rangeClosed(first, last)
            .boxed()
            .collect(Collectors.toList());

        System.out.println("Contents of list:");
        list.stream().forEach(System.out::println);

        System.out.println("=====================");

        // The parallelism number can be the number of cores
        ForkJoinPool customThreadPool = new ForkJoinPool(4);

        int result = customThreadPool.submit(
            () -> list.parallelStream()
                    .reduce(0, Integer::sum)
        ).get();

        System.out.println("Sum of contents, using a custom thread pool: " + result);
    }

}
