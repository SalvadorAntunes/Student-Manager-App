package dataStructures;

/**
 * Array Iterator
 *
 * @author @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 *
 */
class ArrayIterator<E> implements Iterator<E> {
    private E[] elems;
    private int counter;
    private int current;
    
    public ArrayIterator(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        rewind();
    }

    /**
     * Time complexity: O(1) (constant)
     */
    @Override
    public void rewind() {
        current = 0;
    }

    /**
     * Time complexity: O(1) (constant)
     */
    @Override
    public boolean hasNext() {
        return current < counter;
    }

    /**
     * Time complexity: O(1) (constant)
     */
    @Override
    public E next() {
        return elems[current++];
    }

}
