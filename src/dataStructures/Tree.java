package dataStructures;

/**
 * Tree
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic element
 */
import java.io.Serializable;

abstract class Tree<E> implements Serializable {

    /**
     * Root
     */
    protected Node<E> root;

    /**
     * Number of elements
     */
    protected int currentSize;

    public Tree(){
        root=null;
        currentSize=0;
    }

    /**
     * Time complexity: O(1) (constant)
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Time complexity: O(1) (constant)
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     */
    public int size() {
        return currentSize;
    }


    /**
     * Time complexity: O(1) (constant)
     * Return the root of the tree
     * @return the root node
     */
    Node<E> root(){ return root;}

}