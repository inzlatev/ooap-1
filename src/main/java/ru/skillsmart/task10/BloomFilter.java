package ru.skillsmart.task10;

public class BloomFilter {

    public int filter_len;
    private int bitArray;

    public static final int ADD_NIL = 0;
    public static final int ADD_OK = 1;

    public static final int ISVALUE_NIL = 0;
    public static final int ISVALUE_TRUE = 1;
    public static final int ISVALUE_FALSE = 2;

    private int addStatus = ADD_NIL;
    private int isValueStatus = ISVALUE_NIL;

    public BloomFilter(int f_len) {
        this.filter_len = f_len;
        this.bitArray = 0;
    }

    private int hash(String str, int multiplier) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * multiplier + str.charAt(i)) % filter_len;
        }
        return hash;
    }

    public int hash1(String str1) {
        return hash(str1, 17);
    }

    public int hash2(String str1) {
        return hash(str1, 223);
    }

    public void add(String str1) {
        int index1 = hash1(str1);
        int index2 = hash2(str1);
        bitArray |= (1 << index1);
        bitArray |= (1 << index2);
        addStatus = ADD_OK;
    }

    public boolean isValue(String str1) {
        int index1 = hash1(str1);
        int index2 = hash2(str1);
        boolean result = ((bitArray & (1 << index1)) != 0) && ((bitArray & (1 << index2)) != 0);
        isValueStatus = result ? ISVALUE_TRUE : ISVALUE_FALSE;
        return result;
    }

    public int getAddStatus() {
        return addStatus;
    }

    public int getIsValueStatus() {
        return isValueStatus;
    }
}
