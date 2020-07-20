package org.com.concurrency;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * This is an example using Semaphore, to indirectly set bounds on a hashset
 * (taken from java concurrency in practice book)
 */
public class HashSetWithBounds<T> {

    private final Set<T> set;
    private final Semaphore sem;

    public HashSetWithBounds(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        sem = new Semaphore(bound);
    }

    // the acquired permit will be released, if the add operation fails,
    // or if the remove method is successful
    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        }
        finally {
            if (!wasAdded)
                sem.release();
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved)
            sem.release();
        return wasRemoved;
    }

}
