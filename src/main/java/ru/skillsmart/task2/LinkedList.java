package ru.skillsmart.task2;


// 2.1
public class LinkedList<T> {

    private class Node {
        T value;
        Node next;
        Node prev;

        Node(T value) {
            this.value = value;
        }
    }

    public static final int HEAD_NIL = 0, HEAD_OK = 1, HEAD_ERR = 2;
    public static final int TAIL_NIL = 0, TAIL_OK = 1, TAIL_ERR = 2;
    public static final int RIGHT_NIL = 0, RIGHT_OK = 1, RIGHT_ERR = 2;
    public static final int PUT_RIGHT_OK = 1, PUT_RIGHT_ERR = 2;
    public static final int PUT_LEFT_OK = 1, PUT_LEFT_ERR = 2;
    public static final int REMOVE_OK = 1, REMOVE_ERR = 2;
    public static final int ADD_EMPTY_OK = 1, ADD_EMPTY_ERR = 2;
    public static final int REPLACE_OK = 1, REPLACE_ERR = 2;
    public static final int FIND_OK = 1, FIND_ERR = 2;

    private Node head, tail, cursor;
    private int size = 0;

    private int headStatus = HEAD_NIL;
    private int tailStatus = TAIL_NIL;
    private int rightStatus = RIGHT_NIL;
    private int putRightStatus = PUT_RIGHT_ERR;
    private int putLeftStatus = PUT_LEFT_ERR;
    private int removeStatus = REMOVE_ERR;
    private int addEmptyStatus = ADD_EMPTY_ERR;
    private int replaceStatus = REPLACE_ERR;
    private int findStatus = FIND_ERR;

    public LinkedList() {
        clear();
    }

    public void head() {
        if (isValue()) {
            cursor = head;
            headStatus = HEAD_OK;
        } else {
            headStatus = HEAD_ERR;
        }
    }

    public void tail() {
        if (isValue()) {
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
        if (cursor != null) {
            Node newNode = new Node(value);
            newNode.next = cursor.next;
            newNode.prev = cursor;
            if (cursor.next != null) {
                cursor.next.prev = newNode;
            } else {
                tail = newNode;
            }
            cursor.next = newNode;
            size++;
            putRightStatus = PUT_RIGHT_OK;
        } else {
            putRightStatus = PUT_RIGHT_ERR;
        }
    }

    public void putLeft(T value) {
        if (cursor != null) {
            Node newNode = new Node(value);
            newNode.prev = cursor.prev;
            newNode.next = cursor;
            if (cursor.prev != null) {
                cursor.prev.next = newNode;
            } else {
                head = newNode;
            }
            cursor.prev = newNode;
            size++;
            putLeftStatus = PUT_LEFT_OK;
        } else {
            putLeftStatus = PUT_LEFT_ERR;
        }
    }

    public void remove() {
        if (cursor == null) {
            removeStatus = REMOVE_ERR;
            return;
        }
        Node next = cursor.next;
        Node prev = cursor.prev;
        if (prev != null) prev.next = next;
        else head = next;
        if (next != null) next.prev = prev;
        else tail = prev;
        size--;
        cursor = (next != null) ? next : prev;
        removeStatus = REMOVE_OK;
    }

    public void clear() {
        head = tail = cursor = null;
        size = 0;
        headStatus = HEAD_NIL;
        tailStatus = TAIL_NIL;
        rightStatus = RIGHT_NIL;
        putRightStatus = PUT_RIGHT_ERR;
        putLeftStatus = PUT_LEFT_ERR;
        removeStatus = REMOVE_ERR;
        addEmptyStatus = ADD_EMPTY_ERR;
        replaceStatus = REPLACE_ERR;
        findStatus = FIND_ERR;
    }

    public void addToEmpty(T value) {
        if (size == 0) {
            Node newNode = new Node(value);
            head = tail = cursor = newNode;
            size = 1;
            addEmptyStatus = ADD_EMPTY_OK;
        } else {
            addEmptyStatus = ADD_EMPTY_ERR;
        }
    }

    public void addTail(T value) {
        if (size == 0) {
            addToEmpty(value);
        } else {
            Node newNode = new Node(value);
            newNode.prev = tail;
            tail.next = newNode;
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

    public int getHeadStatus() {
        return headStatus;
    }

    public int getTailStatus() {
        return tailStatus;
    }

    public int getRightStatus() {
        return rightStatus;
    }

    public int getPutRightStatus() {
        return putRightStatus;
    }

    public int getPutLeftStatus() {
        return putLeftStatus;
    }

    public int getRemoveStatus() {
        return removeStatus;
    }

    public int getAddEmptyStatus() {
        return addEmptyStatus;
    }

    public int getReplaceStatus() {
        return replaceStatus;
    }

    public int getFindStatus() {
        return findStatus;
    }
}

/** 2.2 Почему операция tail не сводима к другим операциям (если исходить из эффективной реализации)?
 *
 * Если мы не храним ссылку на tail, то для нахождения хвоста понадобится пройти по всем узлам в списке. Но таким образом мы опускаемся на более низкий уровень абстракции, что несовместимо с эффективной реализацией АТД.
 *
 */

/** 2.3 Операция поиска всех узлов с заданным значением, выдающая список таких узлов, уже не нужна. Почему?
 *
 * С помощью операции find(value), передвигая курсор, мы можем найти все узлы с искомым значением, так что это не атомарная операция.
 *
 */