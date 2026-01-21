package dataStructures;

import java.io.Serializable;
/**
 * Node Interface
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic element
 */

interface Node<E> extends Serializable{
    /**
     *  Returns the element of the node
     */
    E getElement();

    /**
     * Update the element
     * @param elem
     */
    void setElement(E elem);
}
