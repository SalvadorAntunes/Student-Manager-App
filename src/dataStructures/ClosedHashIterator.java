package dataStructures;

/**
 * Closed Hash Table Iterator
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 */
public class ClosedHashIterator<E> implements Iterator<E>{
    private E[] elems;
    private int counter;
    private int current;
    private int index;

    public ClosedHashIterator(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        index = 0;
        rewind();
    }

    /**
     * Time complexity: O(1) (constant)
     */
    @Override
    public void rewind() {
        current = 0;
        index = 0;
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
        E e;
        do {
            e = elems[index++];
        } while (e == null);
        current++;
        return e;
    }

}
