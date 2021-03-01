package datastructures.worklists;

import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int size;
    private E[] array;

    public ArrayStack() {
        array = buildArray(10);
    }

    @Override
    public void add(E work) {
        if(size == array.length) {
            E[] temp = buildArray(array.length*2);
            for(int i = 0; i < array.length; i++) {
                temp[i] = array[i];
            }
            array = temp;
        }
        array[size] = work;
        size++;
    }

    //Avoid unchecked cast warning by wraping the casting code in a helper.
    @SuppressWarnings("unchecked")
    private E[] buildArray(int size) {
        return (E[])new Object[size];
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return array[size-1];
    }

    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E result = array[size-1];
        boolean isEmpty = size == 1;
        if (isEmpty) {
            array = buildArray(10);
            size = 0;
        } else {
            size--;
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        array = null;
        size = 0;
    }
}
