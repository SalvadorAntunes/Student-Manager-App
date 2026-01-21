package dataStructures;
import dataStructures.exceptions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * List in Array
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 */
public class ListInArray<E> implements List<E> {

    private static final int FACTOR = 2;
    /**
     * Array of generic elements E.
     */
    private transient E [] elems;

    /**
     * Number of elements in array.
     */
    private transient int counter;


    /**
     * Construtor with capacity.
     * @param dimension - initial capacity of array.
     */
    @SuppressWarnings("unchecked")
    public ListInArray(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }
    /**
     * Time complexity: O(1) (constant)
     *
     * Returns true iff the list contains no elements.
     *
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return counter==0;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns the number of elements in the list.
     *
     * @return number of elements in the list
     */
    public int size() {
        return counter;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns an iterator of the elements in the list (in proper sequence).
     *
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return elems[0];
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        return elems[counter-1];
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     *
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get(int position) {
        if(position<0 || position>=counter)
            throw new IndexOutOfBoundsException();
        return elems[position];
    }

    /**
     * Time complexity
     * Best scenario: O(1) (constant)
     * Worst scenario: O(n) (linear)
     *
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     *
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf(E element) {
        for (int i = 0; i < counter; i++)
            if (elems[i].equals(element))
                return i;
        return -1;
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     */
    public void addFirst(E element) {
        if (counter == elems.length)
            resize();
        for (int i = counter -1; i >= 0; i--)
            elems[i] = elems[i+1];
        elems[0] = element;
        counter++;
    }

    /**
     * Time complexity
     * Best scenario: O(1) (constant)
     * Worst scenario: O(n) (linear)
     *
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     */
    public void addLast(E element) {
        if (counter == elems.length)
            resize();
        elems[counter++] = element;
    }

    private void addMiddle(int position, E element) {
        for (int i = counter-1; i >= position; i--)
            elems[i] = elems[i-1];
        elems[position] = element;
        counter++;
    }

    /**
     * Time complexity
     * Best scenario: O(1) (constant)
     * Worst scenario: O(n) (linear)
     *
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     *
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add(int position, E element) {
        if (position<0 || position>counter)
            throw new InvalidPositionException();
        if (counter == elems.length)
            resize();
        if (position == counter)
            addLast(element);
        else if (position == 0)
            addFirst(element);
        else {
            addMiddle(position, element);
        }
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * Removes and returns the element at the first position in the list.
     *
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst() {
        E temp = elems[0];
        for (int i = 0; i < counter - 1; i++)
            elems[i] = elems[i+1];
        counter--;
        return temp;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Removes and returns the element at the last position in the list.
     *
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast() {
        return elems[--counter];
    }

    private E removeMiddle(int position) {
        E temp = elems[position];
        for (int i = position; i < counter - 1; i++)
            elems[i] = elems[i+1];
        counter--;
        return temp;
    }

    /**
     * Time complexity
     * Best scenario: O(1) (constant)
     * Worst scenario: O(n) (linear)
     *
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     *
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove(int position) {
        if (position<0 || position>counter)
            throw new InvalidPositionException();
        if (counter == 0)
            return removeFirst();
        if (position == counter - 1)
            return removeLast();
        else
            return removeMiddle(position);
    }


    /**
     * Time complexity: O(n) (linear)
     *
     * Copies the current array to a new with double capacity
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newElems = (E[]) new Object[counter*FACTOR];
        for(int i=0; i<counter; i++)
            newElems[i] = elems[i];
        elems = newElems;
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException{
        oos.defaultWriteObject();
        oos.writeInt(counter);
        for (int i = 0; i < counter; i++)
            oos.writeObject(elems[i]);
        oos.flush();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int size = ois.readInt();
        @SuppressWarnings("unchecked")
        E[] newElems = (E[]) new Object[Math.max(size, 1)];
        elems = newElems;
        for (int i = 0; i < size; i++){
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            addLast(element);}
    }
}
