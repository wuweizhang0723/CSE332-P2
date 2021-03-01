package datastructures.worklists;

import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private int size;
    private ListNode front;
    private ListNode back;
    
    public ListFIFOQueue() {
        front = null;
        back = null;
    }

    @Override
    public void add(E work) {
        size++;
        if (front == null) {
            front = new ListNode(work);
            back = front;
        } else {
            back.next = new ListNode(work);
            back = back.next;
        }
    }

    @Override
    public E peek() {
        if (front == null) {
            throw new NoSuchElementException();
        }
        return front.data;
    }

    @Override
    public E next() {
        if (front == null) {
            throw new NoSuchElementException();
        }
        E result = front.data;
        boolean isEmpty = size == 1;
        if (isEmpty) {
            front = null;
            back = null;
        } else {
            front = front.next;
        }
        size--;
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        front = null;
        back = null;
    }

    // Private node class
    private class ListNode{
        E data;
        ListNode next;

        public ListNode(E work) {
            this(work, null);
        }

        public ListNode(E work, ListNode next) {
            this.data = work;
            this.next = next;
        }
    }
}
