package experiments;
import datastructures.dictionaries.AVLTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//what we did above and beyond

public class BFSAVL <K extends Comparable<? super K>, V> extends AVLTree<K, V> {
    public BFSAVL() {
        super();
    }

    public ArrayList<K> BFSHelper(ArrayList<K> order) {
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode temp = q.poll();
            System.out.print(temp.key + " ");
            order.add(temp.key);

            //Enqueue left-subtree
            if (temp.children[0] != null) {
                q.add(temp.children[0]);
            }
            //Enqueue right-subtree
            if (temp.children[1] != null) {
                q.add(temp.children[1]);
            }
        }
        return order;
    }

}
