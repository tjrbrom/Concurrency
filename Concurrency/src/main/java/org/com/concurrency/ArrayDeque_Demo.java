package org.com.concurrency;

import java.util.ArrayDeque;

/**
 * Unlike the java Stack, an ArrayDeque doesn't use locks for it's operations,
 * therefore cases of single-threaded processing do not suffer performance issues.
 *
 * However for multi-threaded purposes, we still have to decorate it in a synchronized way:
 * The benefit here is that, unlike the Stack, we can now have an initial capacity.
 */
public class ArrayDeque_Demo<T> {

    private ArrayDeque<T> deque;

    public ArrayDeque_Demo(int size) {
        this.deque = new ArrayDeque<T>(size);
    }

    public ArrayDeque_Demo() {
        deque = new ArrayDeque<T>();
    }

    public synchronized T pop() {
        return this.deque.pop();
    }

    public synchronized void push(T t) {
        this.deque.push(t);
    }

    public synchronized T peek() {
        return this.deque.peek();
    }

    public synchronized int size() {
        return this.deque.size();
    }

}
