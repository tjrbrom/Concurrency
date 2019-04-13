import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch is a concurrency mechanism to block a thread until other threads have finished running.
 *
 * This is the simplest CountDownLatch example.
 */
public class CountDownLatch_Demo {

    static final int NUM_THREADS = 4;

    static CountDownLatch countDownLatch = new CountDownLatch(NUM_THREADS);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new TestRunnable());
            thread.start();
        }

        System.out.println("Calling countDownLatch.await(), which forces us to wait for the specified number of threads to finish...");
        // when the countdown reaches zero, await terminates
        countDownLatch.await();
        System.out.println("No more waiting... All threads finished executing!");

    }

    static class TestRunnable implements Runnable {

        @Override
        public void run() {

            System.out.println("Calling countDownLatch.countDown() inside run() of " + Thread.currentThread().getName());
            countDownLatch.countDown();
        }

    }

}
