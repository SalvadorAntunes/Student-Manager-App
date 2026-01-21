package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
/**
 * Iterator of keys
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic element
 */
class KeysIterator<E> implements Iterator<E> {

    private final Iterator<Map.Entry<E,?>> it;

    public KeysIterator(Iterator<Map.Entry<E,?>> it) {
        this.it = it;
    }

    /**
     * Time complexity: O(1) (constant)
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return it.hasNext();
    }

    /**
     * Time complexity: O(1) (constant)
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        return it.next().key();
    }

    /**
     * Time complexity: O(1) (constant)
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        it.rewind();
    }
}
