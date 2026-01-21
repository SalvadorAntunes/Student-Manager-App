package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * In-order Binary Tree iterator
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 */
public class InOrderIterator<E> implements Iterator<E> {

    /**
     * Node with the current element
     */
    private BTNode<E> next;

    /**
     * Root Node
     */
    private BTNode<E> root;

    /**
     *
     * @param root
     */
    public  InOrderIterator(BTNode<E> root) {
        this.root=root;
        rewind();
    }

    /**
     * Time complexity: O(1) (constant)
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return next!=null;
    }

    /**
     * Time complexity: O(n) (linear)
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        E elem=next.getElement();
        advance();
        return elem;
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * Advances to the next to iterate
     */
    private void advance() {
        if (next.getRightChild() != null) {
            next = ((BTNode<E>) next.getRightChild()).furtherLeftElement();
        }
        else {
            BTNode<E> parent = (BTNode<E>) next.getParent();
            while (parent != null && next == parent.getRightChild()) {
                next = parent;
                parent = (BTNode<E>) parent.getParent();
            }
            next = parent;
        }
    }


    /**
     * Time complexity: O(n) (linear)
     *
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        if (root==null)
            next=null;
        else
            next=root.furtherLeftElement();
    }
}
