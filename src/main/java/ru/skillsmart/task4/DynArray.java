package ru.skillsmart.task4;

import java.lang.reflect.Array;

public class DynArray<T> {

    public static final int GET_NIL = 0;
    public static final int GET_OK = 1;
    public static final int GET_ERR = 2;

    public static final int INSERT_NIL = 0;
    public static final int INSERT_OK = 1;
    public static final int INSERT_ERR = 2;

    public static final int REMOVE_NIL = 0;
    public static final int REMOVE_OK = 1;
    public static final int REMOVE_ERR = 2;

    public static final int APPEND_NIL = 0;
    public static final int APPEND_OK = 1;

    public static final int MAKEARRAY_OK = 1;
    public static final int MAKEARRAY_ERR = 2;

    private T[] array;
    private int count;
    private int capacity;

    private final Class<T> clazz;

    private int getStatus = GET_NIL;
    private int insertStatus = INSERT_NIL;
    private int removeStatus = REMOVE_NIL;
    private int appendStatus = APPEND_NIL;
    private int makeArrayStatus = MAKEARRAY_OK;

    private static final int MIN_CAPACITY = 16;

    public DynArray(Class<T> clz) {
        this.clazz = clz;
        this.count = 0;
        makeArray(MIN_CAPACITY);
    }

    public void makeArray(int newCapacity) {
        if (newCapacity < count) {
            makeArrayStatus = MAKEARRAY_ERR;
            return;
        }
        T[] newArray = (T[]) Array.newInstance(clazz, newCapacity);
        if (array != null) {
            System.arraycopy(array, 0, newArray, 0, count);
        }
        array = newArray;
        capacity = newCapacity;
        makeArrayStatus = MAKEARRAY_OK;
    }

    public void append(T item) {
        if (count == capacity) {
            makeArray(capacity * 2);
        }
        array[count++] = item;
        appendStatus = APPEND_OK;
    }

    public void insert(T item, int index) {
        if (index < 0 || index > count) {
            insertStatus = INSERT_ERR;
            return;
        }
        if (count == capacity) {
            makeArray(capacity * 2);
        }
        for (int i = count; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = item;
        count++;
        insertStatus = INSERT_OK;
    }

    public void remove(int index) {
        if (index < 0 || index >= count) {
            removeStatus = REMOVE_ERR;
            return;
        }
        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--count] = null;
        reduceIfNeeded();
        removeStatus = REMOVE_OK;
    }

    private void reduceIfNeeded() {
        int threshold = (int) (capacity / 1.5);
        if (count < threshold && capacity > MIN_CAPACITY) {
            int newCapacity = Math.max((int) (capacity / 1.5), MIN_CAPACITY);
            makeArray(newCapacity);
        }
    }

    public T getItem(int i) {
        if (i < 0 || i >= count) {
            getStatus = GET_ERR;
            return null;
        }
        getStatus = GET_OK;
        return array[i];
    }

    public void clear() {
        makeArray(MIN_CAPACITY);
        count = 0;
        getStatus = GET_NIL;
        insertStatus = INSERT_NIL;
        removeStatus = REMOVE_NIL;
        appendStatus = APPEND_NIL;
        makeArrayStatus = MAKEARRAY_OK;
    }

    public int size() {
        return count;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getGetStatus() {
        return getStatus;
    }

    public int getInsertStatus() {
        return insertStatus;
    }

    public int getRemoveStatus() {
        return removeStatus;
    }

    public int getAppendStatus() {
        return appendStatus;
    }

    public int getMakearrayStatus() {
        return makeArrayStatus;
    }
}
