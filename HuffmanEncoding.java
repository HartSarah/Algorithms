import java.util.*;

/*
*The Assignment:
*The goal is to convert a character String into a binary string Huffman
*encoding. Where multiple trees have equal frequency,
*always connect the two trees containing the two lowest value ASCII
*characters, and always put the lowest of those two on the left. For
*instance, every ASCII character is associated with a number, so every
*tree will have a lowest value ASCII character in it - (e.g. capital A is the
*lowest ASCII letter). Always connect the two trees that have the lowest
*characters anywhere inside them, with the lowest on the left. This is
*as simple as saying: take the Priority Queue and as well as sorting on
*frequency, sort on lowest character as well. When two are popped from
*the Priority Queue, put the first to be popped on the left.
*/

public class HuffmanEncoding {
    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        String sentence = in.nextLine();

        int[] array = new int[256]; // an array to store all the frequencies

        for (int i = 0; i < sentence.length(); i++) { // go through the sentence
            array[(int) sentence.charAt(i)]++; // increment the appropriate frequencies
        }

        PriorityQueue<Tree> PQ = new PriorityQueue<Tree>(); // make a priority queue to hold the forest of trees

        for (int i = 0; i < array.length; i++) { // go through frequency array
            if (array[i] > 0) { // print out non-zero frequencies - cast to a char

                Tree myTree = new Tree();
                Node treeRoot = new Node();

                myTree.frequency = array[i];

                treeRoot.letter = (char) i;
                treeRoot.smallestLetter = (char) i;

                myTree.root = treeRoot;

                PQ.add(myTree); // add tree to PQ;
            }
        }

        while (PQ.size() > 1) { // while there are two or more Trees left in the forest

            Node defaultRoot = new Node();

            Tree addTree1 = new Tree();
            Tree addTree2 = new Tree();
            Tree newTree = new Tree();

            addTree1 = PQ.poll(); // take out two trees
            addTree2 = PQ.poll();

            newTree.root = defaultRoot;
            newTree.frequency = addTree1.frequency + addTree2.frequency; // compare frequencies of the two trees

            if (addTree1.frequency < addTree2.frequency) {
                newTree.root.leftChild = addTree1.root;
                newTree.root.rightChild = addTree2.root;
            }

            else if (addTree1.frequency == addTree2.frequency) {
                if ((int) addTree1.root.smallestLetter < (int) addTree2.root.smallestLetter) {
                    newTree.root.leftChild = addTree1.root;
                    newTree.root.rightChild = addTree2.root;
                } else {
                    newTree.root.leftChild = addTree2.root;
                    newTree.root.rightChild = addTree1.root;
                }
            }

            else {
                newTree.root.leftChild = addTree2.root;
                newTree.root.rightChild = addTree1.root;
            }

            if ((int) addTree1.root.smallestLetter < (int) addTree2.root.smallestLetter) {
                newTree.root.smallestLetter = addTree1.root.smallestLetter;
            }

            PQ.add(newTree);
        }

        Tree huffmanTree = PQ.poll(); // complete tree

        for (int x = 0; x < sentence.length(); x++) {
            System.out.print(huffmanTree.getCode(sentence.charAt(x)));
        }
        System.out.println();
    }
}

class Node {

    public char letter = '@'; // stores letter
    public char smallestLetter = '@'; // track the smallest letter in the tree

    public Node leftChild; // this node's left child
    public Node rightChild; // this node's right child

} // end class Node

class Tree implements Comparable<Tree> {
    public Node root; // first node of tree
    public int frequency = 0;

    public Tree() // constructor
    {
        root = null;
    } // no nodes in tree yet

    public int compareTo(Tree object) {
        if (frequency - object.frequency > 0) { // compare the cumulative frequencies of the tree
            return 1;
        } else if (frequency - object.frequency < 0) {
            return -1; // return 1 or -1 depending on whether these frequencies are bigger or smaller
        } else {
            // Sort based on letters
            char a = this.root.smallestLetter;
            char b = object.root.smallestLetter;

            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
            return 0;
        }
    }

    String path = "error"; // this variable will track the path to the letter we're looking for

    public String getCode(char letter) { // we want the code for this letter

        return this._getCode(letter, this.root, ""); // return the path that results
    }

    private String _getCode(char letter, Node current, String path) {
        if (current == null) {
            return null;
        }
        if (current.letter == letter) {
            return path;
        }

        String leftPath = this._getCode(letter, current.leftChild, path + "0");
        if (leftPath != null) {
            return leftPath;
        }

        String rightPath = this._getCode(letter, current.rightChild, path + "1");
        return rightPath;
    }

} // end class Tree