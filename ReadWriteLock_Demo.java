import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A ReadWriteLock interface maintains a pair of locks for read-only and write operations.
 *
 * Read Lock – if no thread acquired the write lock or requested for it then multiple threads can acquire the read lock
 * Write Lock – if no threads are reading or writing then only one thread can acquire the write lock
 */
public class ReadWriteLock_Demo {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private int value;

    void increment() {
        lock.writeLock().lock();
        try {
            // only one thread can get access to it
            value++;
        }
        // we need 'finally' to avoid deadlocks
        finally {
            lock.writeLock().unlock();
        }
    }

    int get() {
        lock.readLock().lock();
        try {
            // Multiple threads can get access to this section if no write operation is in progress
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

}
