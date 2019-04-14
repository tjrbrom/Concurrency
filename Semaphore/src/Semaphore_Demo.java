import java.util.concurrent.Semaphore;

/**
 * Most simple example I can think of, to test and display the power of Java Semaphore.
 */
public class Semaphore_Demo {

    static final int NUM_THREADS = 4;

    // semaphore will be instantiated with a specified number of permits, equal to the number of threads in the executor service pool
    static Semaphore semaphore = new Semaphore(NUM_THREADS);

    public static void main(String[] args) throws InterruptedException {

        // we create here 4 threads, with each one trying to acquire a permit if it is available
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new TestRunnable());
            thread.start();
        }
        Thread.sleep(1000l);

        // executor finished, available semaphore permits should now be zero
        System.out.println("Remaining available permits: " + semaphore.availablePermits());

        // no more permits, therefore tryAcquire returns false
        System.out.println("Calling tryAcquire() returns: " + semaphore.tryAcquire());

        // next, we attempt to release an already acquired permit
        // and check again the number of available semaphore permits
        System.out.println("=========================================");
        System.out.println("Calling release() on semaphore!");
        semaphore.release();
        System.out.println("Remaining available permits, after releasing: " + semaphore.availablePermits());
        // after releasing, we can again acquire
        System.out.println("After releasing, calling tryAcquire() now returns: " + semaphore.tryAcquire());
    }

    static class TestRunnable implements Runnable {

        @Override
        public void run() {

            System.out.println(Thread.currentThread().getName() + " tries to acquire the lock");

            // each time a lock is acquired, specified semaphore permits count is decreased by one
            // returns false if no more permits
            semaphore.tryAcquire();
        }

    }

}
