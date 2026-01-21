package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     *  Iterator of elements to filter.
     */
    private Iterator<E> iterator;

    /**
     *  Filter.
     */
    private Predicate<E> filter;

    /**
     * Node with the next element in the iteration.
     */
    private E nextToReturn;

    /**
     *
     * @param list to be iterated
     * @param criterion filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> criterion) {
        this.iterator = list;
        this.filter = criterion;
        advanceNext();
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns true if next would return an element
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return nextToReturn != null;
    }

    /**
     * Time complexity
     * Best scenario: O(1) (constant)
     * Worst scenario: O(n) (linear)
     *
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        E tmp = nextToReturn;
        advanceNext();
        return tmp;
    }

    /**
     * Time complexity
     * Best scenario: O(1) (constant)
     * Worst scenario: O(n) (linear)
     *
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        iterator.rewind();
        advanceNext();
    }

    /**
     * Time complexity
     * Best scenario: O(1) (constant)
     * Worst scenario: O(n) (linear)
     *
     * Finds the next element matching filter criteria
     */
    private void advanceNext(){
        nextToReturn = null;
        E element;
        while (iterator.hasNext() && nextToReturn == null) {
            element = iterator.next();
            if (filter.check(element)) {
                nextToReturn = element;
            }
        }
    }
}
