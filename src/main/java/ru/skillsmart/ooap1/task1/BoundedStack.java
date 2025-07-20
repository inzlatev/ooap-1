package ru.skillsmart.ooap1.task1;

import java.util.ArrayList;
import java.util.List;

public class BoundedStack<T> {

    public static final int POP_NIL = 0;
    public static final int POP_OK = 1;
    public static final int POP_ERR = 2;

    public static final int PEEK_NIL = 0;
    public static final int PEEK_OK = 1;
    public static final int PEEK_ERR = 2;

    private final List<T> stack;
    private final int capacity;

    private int popStatus;
    private int peekStatus;

    public BoundedStack() {
        this(32);
    }

    public BoundedStack(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = maxSize;
        this.stack = new ArrayList<>();
        this.popStatus = POP_NIL;
        this.peekStatus = PEEK_NIL;
    }

    public void push(T value) {
        if (stack.size() < capacity) {
            stack.add(value);
        }
    }

    public void pop() {
        if (stack.isEmpty()) {
            popStatus = POP_ERR;
        } else {
            stack.remove(stack.size() - 1);
            popStatus = POP_OK;

            if (stack.isEmpty()) {
                peekStatus = PEEK_ERR;
            }
        }
    }

    public void clear() {
        stack.clear();
        popStatus = POP_NIL;
        peekStatus = PEEK_NIL;
    }

    public T peek() {
        if (stack.isEmpty()) {
            peekStatus = PEEK_ERR;
            return null;
        } else {
            peekStatus = PEEK_OK;
            return stack.get(stack.size() - 1);
        }
    }

    public int size() {
        return stack.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPopStatus() {
        return popStatus;
    }

    public int getPeekStatus() {
        return peekStatus;
    }
}