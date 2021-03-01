package p2.sorts;

import java.util.Comparator;


public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {

        helper(array, comparator, 0, array.length);
    }

    private static <E> void helper(E[] array, Comparator<E> comparator, int start, int end) {
        if (start < end-1) {
            int pivot;
            if (((comparator.compare(array[start], array[((end-start)/2)+start]) < 0)
                    && (comparator.compare(array[((end-start)/2)+start], array[end-1]) < 0))
                    || ((comparator.compare(array[end-1], array[((end-start)/2)+start]) < 0)
                    && (comparator.compare(array[((end-start)/2)+start], array[start]) < 0))) {
                pivot = ((end-start)/2)+start;

            } else if ((comparator.compare(array[((end-start)/2)+start], array[start]) < 0
                    && comparator.compare(array[start], array[end-1]) < 0)
                    || (comparator.compare(array[end-1], array[start]) < 0
                    && comparator.compare(array[start], array[((end-start)/2)+start]) < 0)) {
                pivot = start;

            } else {
                pivot = end-1;
            }

            // find smaller and larger half.
            E temp = array[start];
            E pivotValue = array[pivot];
            array[start] = pivotValue;
            array[pivot] = temp;
            int i = start + 1;
            int j = end-1;
            if (i != j) {
                while (i<j) {
                    if (comparator.compare(array[j], pivotValue)>0) {
                        j--;
                    } else if (comparator.compare(array[i], pivotValue)<=0) {
                        i++;
                    } else {
                        temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                }
                // swap with arr[i]
                array[start] = array[i];
                array[i] = pivotValue;
            } else {
                if (comparator.compare(array[i], pivotValue)<0) {
                    temp = array[i];
                    array[i] = pivotValue;
                    array[i-1] = temp;
                }
            }
            helper(array, comparator, i+1, end);
            helper(array, comparator, start, i);
        }

    }
}
