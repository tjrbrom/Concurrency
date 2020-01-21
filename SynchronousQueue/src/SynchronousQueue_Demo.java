import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue helps us coordinate the sharing of information between threads,
 * so that we can be sure that a value has already been set before accessed.
 *
 * Best used in cases where there are always available consumers, ready to take the added element.
 */
public class SynchronousQueue_Demo {

    static SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue();

    public static void main(String[] args) throws InterruptedException {

        Integer randomInt = new Random().nextInt();

        System.out.println("Initial size of the queue is: " + synchronousQueue.size());

        Thread first = new Thread(
            // first runnable puts a number in the queue, while access from other threads is blocked
            () -> {
                try {
                    synchronousQueue.put(randomInt);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            });

        Thread second = new Thread(
            // second runnable waits until the first has finished, then proceeds to take that number from the queue
            () -> {
                try {
                    int taken = synchronousQueue.take();
                    System.out.println("Taking item from queue: " + taken);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            });

        first.start();
        second.start();

        Thread.sleep(1000);

        System.out.println("Both threads finished execution, size of the queue is back to: " + synchronousQueue.size());
    }

}
