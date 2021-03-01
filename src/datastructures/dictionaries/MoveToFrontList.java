package datastructures.dictionaries;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;

/**
 * TODO: Replace this comment with your own as appropriate.
 * This is a MoveToFrontList in which is insert values to the beginning of the
 * list. Also move referenced values to the front.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list. When implementing your iterator, you should 
 *    NOT copy every item to another dictionary/list and return that 
 *    dictionary/list's iterator. 
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListNode front;

    public MoveToFrontList() {
        front = new ListNode();
        super.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V result = null;
        if (super.size == 0) {
            front.key = key;
            front.value = value;
            super.size++;
        } else if (front.key.equals(key)) {
            result = front.value;
            front.value = value;
        } else {
            ListNode temp = front;
            ListNode current = front;
            while (current != null && !current.key.equals(key)) {
                temp = current;
                current = current.next;
            }
            if (current != null) {
                result = current.value;
                temp.next = temp.next.next;
                current.next = front;
                front = current;
                front.value = value;
            } else {
                front = new ListNode(key, value, front);
                super.size++;
            }
        }
        return result;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (super.size == 0) {
            return null;
        } else if (front.key.equals(key)) {
            return front.value;
        } else {
            ListNode temp = front;
            ListNode current = front;
            while (current != null && !current.key.equals(key)) {
                temp = current;
                current = current.next;
            }
            if (current != null) {
                V result = current.value;
                temp.next = temp.next.next;
                current.next = front;
                front = current;
                return result;
            } else {
                return null;
            }
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ListIterator();
    }

    private class ListIterator extends SimpleIterator<Item<K, V>> {
        private ListNode current;

        public ListIterator() {
            this.current = MoveToFrontList.this.front;
        }

        public boolean hasNext() {
            return MoveToFrontList.this.size > 0 && this.current != null;
        }

        public Item<K, V> next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item<K, V> value = new Item<>(this.current.key, this.current.value);
            this.current = this.current.next;
            return value;
        }
    }

    private class ListNode {
        K key;
        V value;
        ListNode next;

        public ListNode() {
            this(null, null, null);
        }

        public ListNode(K key, V value, ListNode next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
