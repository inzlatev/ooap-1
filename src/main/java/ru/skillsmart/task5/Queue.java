package ru.skillsmart.task5;

import java.lang.reflect.Array;

public class Queue<T> {

    public static final int ENQUEUE_NIL = 0;
    public static final int ENQUEUE_OK = 1;

    public static final int DEQUEUE_NIL = 0;
    public static final int DEQUEUE_OK = 1;
    public static final int DEQUEUE_ERR = 2;

    private T[] array;
    private int capacity;
    private int head;
    private int tail;
    private int count;

    private final Class<T> clazz;

    private int enqueueStatus = ENQUEUE_NIL;
    private int dequeueStatus = DEQUEUE_NIL;

    private static final int MIN_CAPACITY = 16;

    public Queue(Class<T> clz) {
        this.clazz = clz;
        makeArray(MIN_CAPACITY);
        clear();
    }

    public void makeArray(int newCapacity) {
        T[] newArray = (T[]) Array.newInstance(clazz, newCapacity);

        for (int i = 0; i < count; i++) {
            newArray[i] = array[(head + i) % capacity];
        }

        array = newArray;
        capacity = newCapacity;
        head = 0;
        tail = count;
    }

    public void enqueue(T item) {
        if (count == capacity) {
            makeArray(capacity * 2);
        }
        array[tail] = item;
        tail = (tail + 1) % capacity;
        count++;
        enqueueStatus = ENQUEUE_OK;
    }

    public T dequeue() {
        if (count == 0) {
            dequeueStatus = DEQUEUE_ERR;
            return null;
        }
        T item = array[head];
        array[head] = null;
        head = (head + 1) % capacity;
        count--;
        dequeueStatus = DEQUEUE_OK;
        return item;
    }

    public int size() {
        return count;
    }

    public void clear() {
        array = (T[]) Array.newInstance(clazz, MIN_CAPACITY);
        capacity = MIN_CAPACITY;
        count = 0;
        head = 0;
        tail = 0;

        enqueueStatus = ENQUEUE_NIL;
        dequeueStatus = DEQUEUE_NIL;
    }

    public int getEnqueueStatus() {
        return enqueueStatus;
    }

    public int getDequeueStatus() {
        return dequeueStatus;
    }
}