package ru.skillsmart.task6;

import ru.skillsmart.task5.Queue;

public class Deque<T> extends Queue<T> {

    public static final int ADD_FRONT_NIL = 0;
    public static final int ADD_FRONT_OK = 1;

    public static final int REMOVE_FRONT_NIL = 0;
    public static final int REMOVE_FRONT_OK = 1;
    public static final int REMOVE_FRONT_ERR = 2;

    public static final int ADD_TAIL_NIL = 0;
    public static final int ADD_TAIL_OK = 1;

    public static final int REMOVE_TAIL_NIL = 0;
    public static final int REMOVE_TAIL_OK = 1;
    public static final int REMOVE_TAIL_ERR = 2;

    private int addFrontStatus = ADD_FRONT_NIL;
    private int removeFrontStatus = REMOVE_FRONT_NIL;
    private int addTailStatus = ADD_TAIL_NIL;
    private int removeTailStatus = REMOVE_TAIL_NIL;

    public Deque(Class<T> clz) {
        super(clz);
    }

    public void addFront(T item) {
        if (super.count == super.capacity) {
            super.makeArray(super.capacity * 2);
        }
        super.head = (super.head - 1 + super.capacity) % super.capacity;
        super.array[super.head] = item;
        super.count++;
        addFrontStatus = ADD_FRONT_OK;
    }

    public void addTail(T item) {
        super.enqueue(item);
        addTailStatus = ADD_TAIL_OK;
    }

    public T removeFront() {
        T item = super.dequeue();
        if (super.getDequeueStatus() == DEQUEUE_OK) {
            removeFrontStatus = REMOVE_FRONT_OK;
        } else {
            removeFrontStatus = REMOVE_FRONT_ERR;
        }
        return item;
    }

    public T removeTail() {
        if (super.count == 0) {
            removeTailStatus = REMOVE_TAIL_ERR;
            return null;
        }
        super.tail = (super.tail - 1 + super.capacity) % super.capacity;
        T item = super.array[super.tail];
        super.array[super.tail] = null;
        super.count--;
        removeTailStatus = REMOVE_TAIL_OK;
        return item;
    }

    public int getAddFrontStatus() {
        return addFrontStatus;
    }

    public int getRemoveFrontStatus() {
        return removeFrontStatus;
    }

    public int getAddTailStatus() {
        return addTailStatus;
    }

    public int get_removeTail_status() {
        return removeTailStatus;
    }
}