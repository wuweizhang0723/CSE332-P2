package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {

    private int size;
    private int front;
    private E[] array;
    private int back;


    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        array = buildArray(capacity);
    }

    @Override
    public void add(E work) {
        if(isFull()) {
            throw new IllegalStateException();
        } else {
            array[back] = work;
            back = (back + 1) % super.capacity();
        }
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }
        return array[front];
    }
    
    @Override
    public E peek(int i) {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }else if(i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        //Hypothetically, index 0 is front, return array[i+front] instead of array[i]
        return array[(i+front) % super.capacity()];
    }
    
    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E result = array[front];
        boolean isEmpty = size == 1;
        if (isEmpty) {
            array = buildArray(super.capacity());
            front = 0;
            back = 0;
        } else {
            front = (front + 1) % super.capacity();
        }
        size--;
        return result;
    }
    
    @Override
    public void update(int i, E value) {
        if(!hasWork()) {
            throw new NoSuchElementException();
        } else if(i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        array[(i + front) % super.capacity()] = value;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        array = buildArray(super.capacity());
        size = 0;
        front = 0;
        back = 0;
    }


    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int minSize = Math.min(this.size(), other.size());
        for(int i = 0; i < minSize; i++) {
            if(this.peek(i).compareTo(other.peek(i)) < 0) {
                return -1;
            } else if(this.peek(i).compareTo(other.peek(i)) > 0) {
                return 1;
            }
        }
        if(this.size() < other.size()) {
            return -1;
        } else if (this.size() > other.size()) {
            return 1;
        }
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here
            return this.compareTo(other) == 0;
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int hashCode = 0;
        for(int i = 0; i < this.size; i ++) {
            hashCode += array[(front + i)% super.capacity()].hashCode() * (front + i);
        }

        return hashCode;
    }



    /*
    //for experiment
    @Override
    public int hashCode() {
        int hashCode = 0;
        //this hashcode only consider the first value and size of the array,
        //so it is worse than the original implementation where we consider all
        //values in the array
        if(this.array!= null) {
            hashCode += array[(front)% super.capacity()].hashCode() * this.size;
        }
        return hashCode;
    }
    */


    //Avoid unchecked cast warning by wraping the casting code in a helper
    @SuppressWarnings("unchecked")
    private E[] buildArray(int size) {
        return (E[])new Comparable[size];
    }

}
