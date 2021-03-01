package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;
import cse332.interfaces.misc.Dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class writeUPEX {

    public static final int NUM_AVG = 20;
    public static final int[] inputSize = {500,1000,1500,2000,2500,3000,3500,4000,4500,5000,5500,6000,6500,7000,7500,8000,8500,9000, 9500, 10000};

    public static ArrayList<Double> AVLInsert = new ArrayList<>();
    public static ArrayList<Double> BSTInsert = new ArrayList<>();

    public static ArrayList<Double> mtfChain = new ArrayList<>();
    public static ArrayList<Double> bstChain = new ArrayList<>();
    public static ArrayList<Double> avlChain = new ArrayList<>();

    //public static ArrayList<Integer> hashTableInput = new ArrayList<>();
    //public static String[] filenames = {"eggs.txt", "dictionary.txt", "dictionary2.txt", "alice.txt"};
    public static ArrayList<Double> hashTime = new ArrayList<>();

    public static ArrayList<Double> bst5 = new ArrayList<>();
    public static ArrayList<Double> avl5 = new ArrayList<>();
    public static ArrayList<Double> chaining5 = new ArrayList<>();
    public static ArrayList<Double> hashtrie5 = new ArrayList<>();


    public static void main(String[] args) throws FileNotFoundException {

        //writeup (5

        ArrayList<AlphabeticString> wordsInFile = getWords("alice.txt");
        dicTest(wordsInFile);
        System.out.println("BST: " + bst5.toString());
        System.out.println("AVL: " + avl5.toString());
        System.out.println("ChainingHashTable: " + chaining5.toString());
        System.out.println("HashTrieMap: " + hashtrie5.toString());



        /*
        //writeup (4)
        ArrayList<AlphabeticString> wordsInFile = getWords("alice.txt");
        for(Integer i: inputSize) {
            hashTest(i, wordsInFile);
        }
        System.out.println("Original Runtime :" + hashTime.toString());
        */


        /*
        //writeup (3)
        for(int i = 0; i < inputSize.length; i++) {
            Chaining(inputSize[i]);
        }
        System.out.println("mtfChain :" + mtfChain.toString());
        System.out.println("bstChain: " + bstChain.toString());
        System.out.println("avlChain: " + avlChain.toString());
        */


        //writeup (2)
        /*
        for (int i = 0; i < inputSize.length; i++) {
            Tree(inputSize[i]);
        }
        System.out.println("AVLInsert :" + AVLInsert.toString());
        System.out.println("BSTInsert: " + BSTInsert.toString());
        */
    }

    //writeup (5)
    public static void dicTest(ArrayList<AlphabeticString> wordsInFile) {
        BinarySearchTree<AlphabeticString, Integer> bst = new BinarySearchTree<>();

        double totalTime = 0;
        for(int rounds = 0; rounds < NUM_AVG; rounds++) {
            long start = System.nanoTime();
            for(AlphabeticString s: wordsInFile) {
                bst.insert(s, 0);
            }
            long end = System.nanoTime();
            if(rounds >= 5){
                totalTime+= (end-start);
                bst5.add((double) (end - start)/Math.pow(10,6));//millis second
            }
        }
        System.out.println("BST Average:" + (totalTime/15)/Math.pow(10,6));


        AVLTree<AlphabeticString, Integer> avl = new AVLTree<>();
        totalTime = 0;
        for(int rounds = 0; rounds < NUM_AVG; rounds++) {
            long start = System.nanoTime();
            for(AlphabeticString s: wordsInFile) {
                avl.insert(s, 0);
            }
            long end = System.nanoTime();
            if(rounds >= 5){
                totalTime+= (end-start);
                avl5.add((double)(end-start)/Math.pow(10,6));//millis second
            }
        }
        System.out.println("AVL Average: " + (totalTime/15)/Math.pow(10,6));

        ChainingHashTable<AlphabeticString, Integer> hashTable = new ChainingHashTable<>(MoveToFrontList::new);
        totalTime = 0;
        for(int rounds = 0; rounds < NUM_AVG; rounds++) {
            long start = System.nanoTime();
            for(AlphabeticString s: wordsInFile) {
                hashTable.insert(s, 0);
            }
            long end = System.nanoTime();
            if(rounds >= 5){
                totalTime+= (end-start);
                chaining5.add((double)(end-start)/Math.pow(10,6));//millis second
            }
        }
        System.out.println("ChainingHashTable Average: " + (totalTime/15)/Math.pow(10,6));



        HashTrieMap<Character, AlphabeticString, Integer> hashTrieMap = new HashTrieMap<>(AlphabeticString.class);
        totalTime = 0;
        for(int rounds = 0; rounds < NUM_AVG; rounds++) {
            long start = System.nanoTime();
            for(AlphabeticString s: wordsInFile) {
                hashTrieMap.insert(s,0);
            }
            long end = System.nanoTime();
            if(rounds >= 5){
                totalTime+= (end-start);
                hashtrie5.add((double)(end - start)/Math.pow(10,6));//millis second
            }
        }
        System.out.println("HashTrieMap Average: " + (totalTime/15)/Math.pow(10,6));

    }

    //writeup (4)
    public static void hashTest(int size, ArrayList<AlphabeticString> wordsInFile){
        ChainingHashTable<AlphabeticString, Integer> hashTable = new ChainingHashTable<>(MoveToFrontList::new);
        double totalTime = 0;
        for(int rounds = 0; rounds < NUM_AVG; rounds++) {
            long start = System.nanoTime();
            for(int i = 0; i < size; i++) {
                hashTable.insert(wordsInFile.get(i), 0);
            }
            long end = System.nanoTime();
            if(rounds >= 5){totalTime+= (end-start);}
        }
        hashTime.add((totalTime/15)/Math.pow(10,6));//millis second
    }
    //writeup (4)
    //return a list of words in the given file
    private static ArrayList<AlphabeticString> getWords(String fileName) throws FileNotFoundException {
        Scanner s = new Scanner(new File(fileName));
        ArrayList<AlphabeticString> wordsInFile = new ArrayList<>();
        while (s.hasNextLine()) {
            String line = s.nextLine();
            Scanner words = new Scanner(line);
            while (words.hasNext()) {
                String word = words.next();
                wordsInFile.add(new AlphabeticString(word));
            }
        }
        return wordsInFile;
    }

    //Writeup (3)
    public static void Chaining(int n) {

        ChainingHashTable<Integer, Integer> mtfTable = new ChainingHashTable<>(MoveToFrontList::new);
        ChainingHashTable<Integer, Integer> bstTable = new ChainingHashTable<>(BinarySearchTree::new);
        ChainingHashTable<Integer, Integer> avlTable = new ChainingHashTable<>(AVLTree::new);

        double mtftotalTime = 0;
        double bsttotalTime = 0;
        double avltotalTime = 0;

        for(int rounds = 0; rounds < NUM_AVG; rounds++) {
            ArrayList<Integer> keys = randomKey(n);

            //mtf
            long start = System.nanoTime();
            for (Integer key : keys) {
                mtfTable.insert(key, 0);
            }
            long end = System.nanoTime();
            if(rounds >= 5){mtftotalTime+= (end-start);}

            //bst
            start = System.nanoTime();
            for (Integer key : keys) {
                bstTable.insert(key, 0);
            }
            end = System.nanoTime();
            if(rounds >= 5){bsttotalTime+= (end-start);}

            //avl
            start = System.nanoTime();
            for (Integer key : keys) {
                avlTable.insert(key, 0);
            }
            end = System.nanoTime();
            if(rounds >= 5){avltotalTime+= (end-start);}

        }
        mtfChain.add((mtftotalTime/15)/Math.pow(10,6));//millis second
        bstChain.add((bsttotalTime/15)/Math.pow(10,6));//millis second
        avlChain.add((avltotalTime/15)/Math.pow(10,6));//millis second
    }

    //writeup (3) helper
    private static ArrayList<Integer> randomKey(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Random random = new Random();
            // generate random number from 0 to n/2
            result.add(random.nextInt((n/2)+1));
        }
        return result;
    }

    /*private static ArrayList<String> randomKey(int n) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 5 * n; i++) {
            int k = (i % n) * 37 % n;
            String key = String.format("%05d", k);
            for (int j = 0; j < k + 1; j ++)
                result.add(key);
        }
        return result;
    }*/

    //writeup (2)
    public static void Tree(int inputSize)  {
        BinarySearchTree<Integer, Integer> BST = new BinarySearchTree<>();
        AVLTree<Integer, Integer> AVL = new AVLTree<>();

        System.out.println("Size of Trees: " + inputSize);

        //insert to build a worst case AVLTree of size inputSize
        double totalTime = 0;
        for(int rounds = 0; rounds < NUM_AVG; rounds++) {
            long start = System.nanoTime();
            for (int i = 0; i < inputSize; i++) {
                AVL.insert(i, 0);
            }

            //runtime for find
            AVL.find(inputSize + 1);
            long end = System.nanoTime();
            if(rounds >= 5){totalTime+= (end-start);}
        }
        //System.out.println("AVL construct: " + buildTime/10 + " nanoseconds.");
        //System.out.println("AVL find: " + findTime/10 + " nanoseconds.");
        AVLInsert.add((totalTime/15)/Math.pow(10,6));//millis second

        //insert to build a worst case BST of size inputSize
        totalTime = 0;
        for(int rounds = 0; rounds < NUM_AVG; rounds++) {
            long start = System.nanoTime();
            for (int i = 0; i < inputSize; i++) {
                BST.insert(i, 0);
            }


            //runtime for find
            BST.find(inputSize + 1);
            long end = System.nanoTime();
            if(rounds >= 5) {totalTime+= (end-start);}
        }
        //System.out.println("BST construct: " + buildTime/10 + " nanoseconds.");
        //System.out.println("BST find: " + findTime/10 + " nanoseconds.");
        BSTInsert.add((totalTime/15)/Math.pow(10,6)); //millisecond
    }

}
