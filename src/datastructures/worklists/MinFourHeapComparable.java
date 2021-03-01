package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;

    public MinFourHeapComparable() {
        //what should be the initial size???
        data = buildArray(10);
        size = 0;
    }

    @Override
    public boolean hasWork() {
        return this.size() > 0;
    }

    @Override
    public void add(E work) {
        if(size == data.length) {
            E[] temp = buildArray(data.length*2);
            for(int i = 0; i < data.length; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
        data[size] = work;
        percolateUp();
        size++;
    }
    //helper function that percolate up after adding
    private void percolateUp() {
        int curr = size;
        while(curr != 0) {
            int parent = (curr-1)/4;
            if(data[curr].compareTo(data[parent]) < 0) { // if child is small
                E temp = data[parent];
                data[parent] = data[curr];
                data[curr] = temp;
                curr = parent;
            } else {
                break;
            }
        }
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E result = data[0];
        boolean isEmpty = size == 1;
        if (isEmpty) {
            data = buildArray(10);
        } else {
            data[0] = data[size-1];
            percolateDown();
        }
        size--;
        return result;
    }

    private void percolateDown() {
        int curr = 0;
        int child = curr*4 + 1;
        while(child < size) {
            int minChild = child;
            for(int i = 1; i < 4; i++) {
                if(child+i < size) {
                    if(data[minChild].compareTo(data[child+i]) > 0) {
                        minChild = child+i;
                    }
                }
            }
            if(data[minChild].compareTo(data[curr]) < 0) {
                E temp = data[minChild];
                data[minChild] = data[curr];
                data[curr] = temp;
                curr = minChild;
                child = curr*4 + 1;
            } else {
                break;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        data = buildArray(10);
        size = 0;
    }

    //Avoid unchecked cast warning by wraping the casting code in a helper.
    @SuppressWarnings("unchecked")
    private E[] buildArray(int size) {
        return (E[])new Comparable[size];
    }
}
