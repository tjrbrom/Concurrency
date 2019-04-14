import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * This is a simple demonstration I created to test and understand the CyclicBarrier, a java Thread synchronization mechanism.
 * <p>
 * Read the comments below for a better understanding of this great tool.
 */
public class CyclicBarrier_Demo {

    static final int NUM_THREADS = 4;

    static CyclicBarrier cyclicBarrier;

    public static void main(String args[]) {

        System.out.println("Number of threads to reach the barrier : " + NUM_THREADS);

        // When 4 threads have called await(), the barrier is broken, and it's own runnable executes !
        cyclicBarrier = new CyclicBarrier(NUM_THREADS,
                () -> System.out.println("Limit reached, barrier now proceeds to execute it's own Runnable."));

        // we create some threads, that will call await on the barrier
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new TestRunnable());
            thread.start();
        }

    }

    // Notice below, the barrier's await() method being called by each Runnable
    static class TestRunnable implements Runnable {

        @Override
        public void run() {

            try {
                System.out.println(Thread.currentThread().getName()
                        + ": Calling await(), waiting for others to reach barrier.");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                // ignored
            } catch (BrokenBarrierException e) {
                // also ignored
            }
        }

    }

}
