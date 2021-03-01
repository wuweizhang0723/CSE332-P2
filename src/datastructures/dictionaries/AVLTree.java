package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * This is a AVLTree that saves key-value paires. The input data are sorted by keys
 * with a BST logic. The left and reight subtrees are forced to have a height difference
 * smaller than 1.
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V>  {
    // TODO: Implement me!
    public AVLTree() {
        super();
    }

    public class AVLNode extends BSTNode {
        int height;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V oldValue = find(key);
        this.root = insertHelper(cast(this.root), key, value);
        return oldValue;
    }

    private AVLNode insertHelper(AVLNode node, K key, V value) {
        if(node == null) {
            this.size++;
            return new AVLNode(key, value);
        }
        int direction = Integer.signum(key.compareTo(node.key));
        if(direction < 0) {
            node.children[0] = insertHelper(lAVL(node), key, value);
        } else if (direction > 0) {
            node.children[1] = insertHelper(rAVL(node), key, value);
        } else {
            node.value = value;
        }
        return balance(node);
    }

    private AVLNode balance(AVLNode node) {
        if(node == null) {
            return node;
        }
        if(heightDiff(node)> 1) {
            if (heightDiff(lAVL(node))>0) {
                node = rotateWithL(node);
            } else {
                node = doubleWithL(node);
            }
        } else if(heightDiff(node) < -1) {
            if (heightDiff(rAVL(node)) < 0) {
                node = rotateWithR(node);
            } else {
                node = doubleWithR(node);
            }
        }
        node.height = Math.max(height(lAVL(node)),height(rAVL(node)))+1;
        return node;
    }

    private int heightDiff(BSTNode node) {
        return height(lAVL(node)) - height(rAVL(node));
    }

    //the same as rotate with right child
    private AVLNode rotateWithR(AVLNode node) {
        AVLNode temp = rAVL(node);
        node.children[1] = temp.children[0];
        temp.children[0] = node;
        node.height = Math.max(height(lAVL(node)),height(rAVL(node)))+1;
        temp.height = Math.max(height(rAVL(temp)),height(node))+1;
        return temp;
    }

    private AVLNode rotateWithL(AVLNode node) {
        AVLNode temp = lAVL(node);
        node.children[0] = temp.children[1];
        temp.children[1] = node;
        node.height = Math.max(height(lAVL(node)),height(rAVL(node)))+1;
        temp.height = Math.max(height(lAVL(temp)),height(node))+1;
        return temp;
    }

    //Cast a BSTNode to AVLNode
    private AVLNode cast(BSTNode node) {
        return (AVLNode)node;
    }

    //Get the left child
    private AVLNode lAVL(BSTNode node) {
        return (AVLNode)node.children[0];
    }

    private AVLNode rAVL(BSTNode node) {
        return (AVLNode)node.children[1];
    }

    private AVLNode doubleWithL(AVLNode node) {
        node.children[0] = rotateWithR(lAVL(node));
        return rotateWithL(node);
    }
    private AVLNode doubleWithR(AVLNode node) {
        node.children[1] = rotateWithL(rAVL(node));
        return rotateWithR(node);
    }

    //Get height of a node
    private int height(AVLNode node) {
        if(node == null) {
            return -1;
        }
        return node.height;
    }
}
