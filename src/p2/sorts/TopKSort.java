package p2.sorts;

import java.util.Comparator;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);
        //Never put more tha k values into the array
        for (int i = 0; i < Math.min(k, array.length); i++) {
            heap.add(array[i]);
        }

        for (E e : array) {
            if (comparator.compare(e, heap.peek()) > 0) {
                heap.add(e);
                heap.next(); //remove the minvalue
            }
        }

        for (int i = 0; i < array.length; i++) {
            if (i < k) {
                array[i] = heap.next();
            } else {
                //Other value as null
                array[i] = null;
            }
        }
    }
}
