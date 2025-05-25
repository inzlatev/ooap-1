package ru.skillsmart.task3;

public class ParentList<T> {

    protected class Node {
        T value;
        Node next;
        Node prev;

        Node(T value) {
            this.value = value;
        }
    }

    public static final int LEFT_NIL = 0, LEFT_OK = 1, LEFT_ERR = 2;
    public static final int RIGHT_NIL = 0, RIGHT_OK = 1, RIGHT_ERR = 2;
    public static final int REMOVE_NIL = 0, REMOVE_OK = 1, REMOVE_ERR = 2;
    public static final int PUT_RIGHT_NIL = 0, PUT_RIGHT_OK = 1, PUT_RIGHT_ERR = 2;
    public static final int PUT_LEFT_NIL = 0, PUT_LEFT_OK = 1, PUT_LEFT_ERR = 2;
    public static final int HEAD_NIL = 0, HEAD_OK = 1, HEAD_ERR = 2;
    public static final int TAIL_NIL = 0, TAIL_OK = 1, TAIL_ERR = 2;
    public static final int REPLACE_NIL = 0, REPLACE_OK = 1, REPLACE_ERR = 2;
    public static final int FIND_NIL = 0, FIND_OK = 1, FIND_ERR = 2;

    protected Node head, tail, cursor;
    protected int size;

    protected int leftStatus = LEFT_NIL;
    protected int rightStatus = RIGHT_NIL;
    protected int removeStatus = REMOVE_NIL;
    protected int putRightStatus = PUT_RIGHT_NIL;
    protected int putLeftStatus = PUT_LEFT_NIL;
    protected int headStatus = HEAD_NIL;
    protected int tailStatus = TAIL_NIL;
    protected int replaceStatus = REPLACE_NIL;
    protected int findStatus = FIND_NIL;

    public void clear() {
        head = tail = cursor = null;
        size = 0;
        leftStatus = LEFT_NIL;
        rightStatus = RIGHT_NIL;
        removeStatus = REMOVE_NIL;
        putRightStatus = PUT_RIGHT_NIL;
        putLeftStatus = PUT_LEFT_NIL;
        headStatus = HEAD_NIL;
        tailStatus = TAIL_NIL;
        replaceStatus = REPLACE_NIL;
        findStatus = FIND_NIL;
    }

    public void addToEmpty(T value) {
        if (size != 0) return;
        Node node = new Node(value);
        head = tail = cursor = node;
        size = 1;
    }

    public void head() {
        if (size > 0) {
            cursor = head;
            headStatus = HEAD_OK;
        } else {
            headStatus = HEAD_ERR;
        }
    }

    public void tail() {
        if (size > 0) {
            cursor = tail;
            tailStatus = TAIL_OK;
        } else {
            tailStatus = TAIL_ERR;
        }
    }

    public void right() {
        if (cursor != null && cursor.next != null) {
            cursor = cursor.next;
            rightStatus = RIGHT_OK;
        } else {
            rightStatus = RIGHT_ERR;
        }
    }

    public void putRight(T value) {
        if (cursor == null) {
            putRightStatus = PUT_RIGHT_ERR;
            return;
        }
        Node newNode = new Node(value);
        newNode.prev = cursor;
        newNode.next = cursor.next;
        if (cursor.next != null) {
            cursor.next.prev = newNode;
        } else {
            tail = newNode;
        }
        cursor.next = newNode;
        size++;
        putRightStatus = PUT_RIGHT_OK;
    }

    public void putLeft(T value) {
        if (cursor == null) {
            putLeftStatus = PUT_LEFT_ERR;
            return;
        }
        Node newNode = new Node(value);
        newNode.next = cursor;
        newNode.prev = cursor.prev;
        if (cursor.prev != null) {
            cursor.prev.next = newNode;
        } else {
            head = newNode;
        }
        cursor.prev = newNode;
        size++;
        putLeftStatus = PUT_LEFT_OK;
    }

    public void remove() {
        if (cursor == null) {
            removeStatus = REMOVE_ERR;
            return;
        }
        Node prev = cursor.prev;
        Node next = cursor.next;
        if (prev != null) prev.next = next;
        else head = next;
        if (next != null) next.prev = prev;
        else tail = prev;
        size--;
        cursor = (next != null) ? next : prev;
        removeStatus = REMOVE_OK;
    }

    public void addTail(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node newNode = new Node(value);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            size++;
        }
    }

    public void replace(T value) {
        if (cursor != null) {
            cursor.value = value;
            replaceStatus = REPLACE_OK;
        } else {
            replaceStatus = REPLACE_ERR;
        }
    }

    public void find(T value) {
        Node current = cursor;
        while (current != null && current.next != null) {
            current = current.next;
            if ((value == null && current.value == null) ||
                    (value != null && value.equals(current.value))) {
                cursor = current;
                findStatus = FIND_OK;
                return;
            }
        }
        findStatus = FIND_ERR;
    }

    public void removeAll(T value) {
        head();
        while (isValue()) {
            if ((value == null && get() == null) ||
                    (value != null && value.equals(get()))) {
                remove();
            } else {
                right();
            }
        }
    }

    public T get() {
        return (cursor != null) ? cursor.value : null;
    }

    public int size() {
        return size;
    }

    public boolean isHead() {
        return cursor != null && cursor == head;
    }

    public boolean isTail() {
        return cursor != null && cursor == tail;
    }

    public boolean isValue() {
        return cursor != null;
    }

    public int getLeftStatus() {
        return leftStatus;
    }

    public int getRightStatus() {
        return rightStatus;
    }

    public int getRemoveStatus() {
        return removeStatus;
    }

    public int getPutRightStatus() {
        return putRightStatus;
    }

    public int getPutLeftStatus() {
        return putLeftStatus;
    }

    public int getHeadStatus() {
        return headStatus;
    }

    public int getTailStatus() {
        return tailStatus;
    }

    public int getReplaceStatus() {
        return replaceStatus;
    }

    public int getFindStatus() {
        return findStatus;
    }
}

class LinkedList<T> extends ParentList<T> {
    // Наследует двусвязную структуру, но не даёт пользователю доступа к left()
}

class TwoWayList<T> extends ParentList<T> {
    public void left() {
        if (cursor != null && cursor.prev != null) {
            cursor = cursor.prev;
            leftStatus = LEFT_OK;
        } else {
            leftStatus = LEFT_ERR;
        }
    }
}
