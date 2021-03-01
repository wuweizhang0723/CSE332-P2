package datastructures.dictionaries;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;
import datastructures.worklists.ArrayStack;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            //this.pointers = new HashMap<>();
            this.pointers = new ChainingHashTable<>(() -> new MoveToFrontList<>());
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ArrayStack<Entry<A, HashTrieNode>> entries = new ArrayStack<>();
            for(Item<A, HashTrieNode> value : this.pointers) {
                entries.add(new AbstractMap.SimpleEntry(value.key, value.value));
            }
            return entries.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode curr = (HashTrieNode)this.root;

        V result;
        for(A letter : key) {
            if(curr.pointers.find(letter) == null) {
                curr.pointers.insert(letter, new HashTrieNode());
            }
            curr = curr.pointers.find(letter);
        }
        result = curr.value;
        if (result == null) {
            this.size++;
        }
        curr.value = value;
        return result;
    }

    @Override
    public V find(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode)this.root;
        for(A letter : key) {
            if(curr == null || curr.pointers.find(letter) == null) {
                return null;
            }
            curr = curr.pointers.find(letter);
        }
        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        if(this.root == null) {
            return false;
        }
        HashTrieNode curr = (HashTrieNode)this.root;
        for(A letter : key) {
            if(curr.pointers.find(letter) == null){
                return false;
            }
            curr = curr.pointers.find(letter);
        }
        return true;
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

}
