package ru.skillsmart.task7;

public class HashTable {
    public int size;
    public int step = 3;
    public String[] slots;

    public static final int PUT_NIL = 0;
    public static final int PUT_OK = 1;
    public static final int PUT_ERR = 2;

    public static final int FIND_NIL = 0;
    public static final int FIND_OK = 1;
    public static final int FIND_ERR = 2;

    private int putStatus = PUT_NIL;
    private int findStatus = FIND_NIL;

    public HashTable(int sz) {
        this.size = sz;
        this.slots = new String[size];
        for (int i = 0; i < size; i++) {
            slots[i] = null;
        }
    }

    public int hashFun(String value) {
        int hash = 0;
        for (char ch : value.toCharArray()) {
            hash += ch;
        }
        return hash % size;
    }

    public int seekSlot(String value) {
        int index = hashFun(value);
        for (int i = 0; i < size; i++) {
            int tryIndex = (index + i * step) % size;
            if (slots[tryIndex] == null) {
                return tryIndex;
            }
        }
        return -1;
    }

    public int put(String value) {
        int slot = seekSlot(value);
        if (slot != -1) {
            slots[slot] = value;
            putStatus = PUT_OK;
            return slot;
        } else {
            putStatus = PUT_ERR;
            return -1;
        }
    }

    public int find(String value) {
        int index = hashFun(value);
        for (int i = 0; i < size; i++) {
            int tryIndex = (index + i * step) % size;
            if (slots[tryIndex] == null) break;
            if (value.equals(slots[tryIndex])) {
                findStatus = FIND_OK;
                return tryIndex;
            }
        }
        findStatus = FIND_ERR;
        return -1;
    }

    public int getPutStatus() {
        return putStatus;
    }

    public int getFindStatus() {
        return findStatus;
    }
}