package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator of values
 *
 * @author @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic element
 */
class ValuesIterator<E> implements Iterator<E> {

    private final Iterator<Map.Entry<?,E>> it;

    public ValuesIterator(Iterator<Map.Entry<?,E>> it) {
        this.it = it;
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
       return it.hasNext();
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
        if (!it.hasNext())
            throw new NoSuchElementException();
	    return it.next().value();
    }

    /**
     * Time complexity: O(n) (linear)
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    @Override
    public void rewind() {
       it.rewind();
    }
}
