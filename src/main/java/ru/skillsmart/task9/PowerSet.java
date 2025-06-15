package ru.skillsmart.task9;

public class PowerSet {

    private final int size = 20000;
    private final int step = 3;
    private String[] slots;

    public static final int PUT_NIL = 0;
    public static final int PUT_OK = 1;
    public static final int PUT_EXISTS = 2;
    public static final int PUT_ERR = 3;

    public static final int REMOVE_NIL = 0;
    public static final int REMOVE_OK = 1;
    public static final int REMOVE_ERR = 2;

    public static final int GET_NIL = 0;
    public static final int GET_TRUE = 1;
    public static final int GET_FALSE = 2;

    private int putStatus = PUT_NIL;
    private int removeStatus = REMOVE_NIL;
    private int getStatus = GET_NIL;

    private int count;

    public PowerSet() {
        slots = new String[size];
    }

    private int hashFun(String value) {
        int hash = 0;
        for (char ch : value.toCharArray()) {
            hash += ch;
        }
        return hash % size;
    }

    private int seekSlot(String value) {
        int index = hashFun(value);
        for (int i = 0; i < size; i++) {
            int tryIndex = (index + i * step) % size;
            if (slots[tryIndex] == null || slots[tryIndex].equals(value)) {
                return tryIndex;
            }
        }
        return -1;
    }

    public void put(String value) {
        int slot = seekSlot(value);
        if (slot == -1) {
            putStatus = PUT_ERR;
            return;
        }
        if (slots[slot] != null && slots[slot].equals(value)) {
            putStatus = PUT_EXISTS;
        } else {
            slots[slot] = value;
            count++;
            putStatus = PUT_OK;
        }
    }

    public boolean get(String value) {
        int index = hashFun(value);
        for (int i = 0; i < size; i++) {
            int tryIndex = (index + i * step) % size;
            if (slots[tryIndex] == null) break;
            if (slots[tryIndex].equals(value)) {
                getStatus = GET_TRUE;
                return true;
            }
        }
        getStatus = GET_FALSE;
        return false;
    }

    public boolean remove(String value) {
        int index = hashFun(value);
        for (int i = 0; i < size; i++) {
            int tryIndex = (index + i * step) % size;
            if (slots[tryIndex] == null) break;
            if (slots[tryIndex].equals(value)) {
                slots[tryIndex] = null;
                count--;
                removeStatus = REMOVE_OK;
                return true;
            }
        }
        removeStatus = REMOVE_ERR;
        return false;
    }

    public int size() {
        return count;
    }

    public int getPutStatus() {
        return putStatus;
    }

    public int getRemoveStatus() {
        return removeStatus;
    }

    public int getGetStatus() {
        return getStatus;
    }

    // Множественные операции

    public PowerSet intersection(PowerSet set2) {
        PowerSet result = new PowerSet();
        for (String val : this.slots) {
            if (val != null && set2.get(val)) {
                result.put(val);
            }
        }
        return result;
    }

    public PowerSet union(PowerSet set2) {
        PowerSet result = new PowerSet();
        for (String val : this.slots) {
            if (val != null) result.put(val);
        }
        for (String val : set2.slots) {
            if (val != null) result.put(val);
        }
        return result;
    }

    public PowerSet difference(PowerSet set2) {
        PowerSet result = new PowerSet();
        for (String val : this.slots) {
            if (val != null && !set2.get(val)) {
                result.put(val);
            }
        }
        return result;
    }

    public boolean isSubset(PowerSet set2) {
        for (String val : set2.slots) {
            if (val != null && !this.get(val)) return false;
        }
        return true;
    }

    public boolean equals(PowerSet set2) {
        return this.isSubset(set2) && set2.isSubset(this);
    }
}
