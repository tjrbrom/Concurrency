import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList
 *
 * Best used for reading than writing, as additional internal immutable copies are
 * generated internally with each add or remove call
 * Particularly useful for iterating purposes
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {

        CopyOnWriteArrayList<Integer> nums
                = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});

        // When we create an iterator for the CopyOnWriteArrayList, we get an
        // immutable snapshot of the data in the list when iterator() was called.
        Iterator<Integer> iterator = nums.iterator();

        nums.add(123);

        // The newly added number is not contained in the iterator,
        // since it was added after the iterator was created:
        iterator.forEachRemaining(System.out::println);

        // This collection is thread-safe, since iterations will not be affected
        // by other threads adding and removing elements
    }

}
