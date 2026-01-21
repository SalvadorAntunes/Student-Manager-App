package dataStructures;
import java.io.*;

/**
 * Singly List Node
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 *
 */
class SinglyListNode<E> implements Serializable {

    /**
     * Element stored in the node.
     */
    private transient E element;

    /**
     * (Pointer to) the next node.
     */
    private transient SinglyListNode<E> next;

    /**
     *
     * @param theElement - The element to be contained in the node
     * @param theNext - the next node
     */
    public SinglyListNode( E theElement, SinglyListNode<E> theNext ) {
        element = theElement;
        next = theNext;
    }

    /**
     *
     * @param theElement to be contained in the node
     */
    public SinglyListNode( E theElement ) {
        this(theElement, null);
    }

    /**
     *
     * @return the element contained in the node
     */
    public E getElement( ) {
        return element;
    }

    /**
     *
     * @return the next node
     */
    public SinglyListNode<E> getNext( ) {
        return next;
    }

    /**
     *
     * @param newElement - New element to replace the current element
     */
    public void setElement( E newElement ) {
        element = newElement;
    }

    /**
     *
     * @param newNext - node to replace the next node
     */
    public void setNext( SinglyListNode<E> newNext ) {
        next = newNext;
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(element);
        oos.writeObject(next);
        oos.flush();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        @SuppressWarnings("unchecked")
        E element = (E) ois.readObject();
        this.element = element;
        @SuppressWarnings("unchecked")
        SinglyListNode<E> next = (SinglyListNode<E>) ois.readObject();
        this.next = next;
    }
}
