import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * ConcurrentLinkedDeque is a thread-safe implementation of the Deque interface.
 *
 * Since it is lock-free by design, it avoids potential deadlock issues.
 */
public class ConcurrentLinkedDeque_Demo {

    public static void main(String[] args) {

        ConcurrentLinkedDeque<Integer>
                deque = new ConcurrentLinkedDeque<Integer>();

        deque.addFirst(5);
        deque.addFirst(2);
        deque.addFirst(7);
        deque.addFirst(1);

        System.out.println("ConcurrentLinkedDeque: "
                + deque);

        System.out.println("Returning the Last element, using getLast(): "
                + deque.getLast());

        System.out.println("Checking the first element, using peekFirst(): "
                + deque.peekFirst());

        System.out.println("Removing the last element, using removeLast(): " + deque.removeLast());

        System.out.println("ConcurrentLinkedDeque: "
                + deque);
    }

}
