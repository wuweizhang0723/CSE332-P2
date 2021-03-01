package datastructures.dictionaries;

import java.util.Iterator;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

/**
 * TODO: Replace this comment with your own as appropriate.
 * This is a chaininghastable that saves key-value pairs. We calculate the hashcode
 * of each input using the keys.

 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private final Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K,V>[] arr;
    private int primeIndex;

    // not pretty sure which primes should be reasonable as table size.
    private final int[] primes = {367, 769, 1549, 3089, 6229, 12451, 24923, 49991, 99907, 199967, 223087};


    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        arr = buildArr(157);
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        V oldValue = find(key);
        if (oldValue == null) {
            this.size++;
        }

        int index = Math.abs(key.hashCode()% arr.length);
        if (arr[index] == null) {
            arr[index] = newChain.get();
            //arr[index].insert(key, value);
        }
        arr[index].insert(key, value);


        if (((double) this.size / arr.length)>=1) {
            // rehash!!!!
            rehash();
        }
        return oldValue;

    }

    private void rehash() {
        Dictionary<K,V>[] temp;
        //changed this value
        if (primeIndex >= primes.length) {
            temp = buildArr(arr.length * 2 + 1);
        } else {
            temp = buildArr(primes[primeIndex]);
            primeIndex++;
        }
        for (Dictionary<K,V> bucket : arr) {
            if (bucket != null) {
                for (Item<K, V> pair : bucket) {
                    int i = Math.abs(pair.key.hashCode() % temp.length);
                    if (temp[i] == null) {
                        temp[i] = newChain.get();
                    }
                    temp[i].insert(pair.key, pair.value);
                }
            }
        }
        arr = temp;

    }

    @SuppressWarnings("unchecked")
    private Dictionary<K,V>[] buildArr(int size) {
        return (Dictionary<K,V>[])new Dictionary[size];
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int index = Math.abs(key.hashCode()% arr.length);
        if (arr[index] == null) {
            return null;
        }
        return arr[index].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new CHTIterator();
    }
    @SuppressWarnings("unchecked")
    private class CHTIterator extends SimpleIterator<Item<K, V>> {
        private final Iterator<Item<K, V>>[] iterators;
        private int bucketIndex;
        private int count;

        public CHTIterator() {
            iterators = (Iterator<Item<K, V>>[]) new Iterator[arr.length];
            bucketIndex = 0;
            count = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != null) {
                    iterators[i] = arr[i].iterator();
                }
            }
        }

        public boolean hasNext() {
            return (count < size);
        }

        public Item<K, V> next() {
            while (bucketIndex < arr.length - 1 && (iterators[bucketIndex] == null || !iterators[bucketIndex].hasNext())) {
                bucketIndex++;
            }

            count++;
            return iterators[bucketIndex].next();
        }
    }
}
