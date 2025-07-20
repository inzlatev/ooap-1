package ru.skillsmart.ooap1.task8;

import java.lang.reflect.Array;

public class NativeDictionary<T> {

    public int size;
    public String[] slots;
    public T[] values;

    private final int step = 3;

    public static final int PUT_NIL = 0;
    public static final int PUT_OK = 1;
    public static final int PUT_ERR = 2;

    public static final int GET_NIL = 0;
    public static final int GET_OK = 1;
    public static final int GET_ERR = 2;

    public static final int ISKEY_NIL = 0;
    public static final int ISKEY_OK = 1;
    public static final int ISKEY_ERR = 2;

    private int putStatus = PUT_NIL;
    private int getStatus = GET_NIL;
    private int isKeyStatus = ISKEY_NIL;

    public NativeDictionary(int sz, Class<T> clazz) {
        this.size = sz;
        this.slots = new String[size];
        this.values = (T[]) Array.newInstance(clazz, size);
    }

    public int hashFun(String key) {
        int hash = 0;
        for (char ch : key.toCharArray()) {
            hash += ch;
        }
        return hash % size;
    }

    public boolean isKey(String key) {
        int index = hashFun(key);
        for (int i = 0; i < size; i++) {
            int tryIndex = (index + i * step) % size;
            if (slots[tryIndex] == null) {
                isKeyStatus = ISKEY_ERR;
                return false;
            }
            if (slots[tryIndex].equals(key)) {
                isKeyStatus = ISKEY_OK;
                return true;
            }
        }
        isKeyStatus = ISKEY_ERR;
        return false;
    }

    public void put(String key, T value) {
        int index = hashFun(key);
        for (int i = 0; i < size; i++) {
            int tryIndex = (index + i * step) % size;
            if (slots[tryIndex] == null || slots[tryIndex].equals(key)) {
                slots[tryIndex] = key;
                values[tryIndex] = value;
                putStatus = PUT_OK;
                return;
            }
        }
        putStatus = PUT_ERR;
    }

    public T get(String key) {
        int index = hashFun(key);
        for (int i = 0; i < size; i++) {
            int tryIndex = (index + i * step) % size;
            if (slots[tryIndex] == null) {
                getStatus = GET_ERR;
                return null;
            }
            if (slots[tryIndex].equals(key)) {
                getStatus = GET_OK;
                return values[tryIndex];
            }
        }
        getStatus = GET_ERR;
        return null;
    }

    public int getPutStatus() {
        return putStatus;
    }

    public int getGetStatus() {
        return getStatus;
    }

    public int getIsKeyStatus() {
        return isKeyStatus;
    }
}
