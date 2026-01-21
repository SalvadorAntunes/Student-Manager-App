package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * Binary Tree Node
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 */
class BTNode<E> implements Node<E> {

    /**
     * Element
     */
    private transient E element;

    /**
     * Pointer to the father
     */
    private transient Node<E> parent;

    /**
     * Pointer to the left child
     */
    private transient Node<E> leftChild;

    /**
     * Pointer to the right child
     */
    private transient Node<E> rightChild;

    /**
     * Constructor
     * @param elem
     */
    BTNode(E elem){
        this(elem,null,null,null);
    }

    /**
     * Constructor
     * @param elem
     * @param parent
     */
    BTNode(E elem, BTNode<E> parent) {
        this(elem,parent,null,null);
    }
    /**
     * Constructor
     * @param elem
     * @param parent
     * @param leftChild
     * @param rightChild
     */
    BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild){
        this.element = elem;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     *  Returns the element of the node
     * @return element
     */
    public E getElement() {
        return element;
    }
    /**
     * Time complexity: O(1) (constant)
     *
     * Returns the left son of node
     * @return left child node
     */
    public Node<E> getLeftChild(){
        return leftChild;
    }
    /**
     * Time complexity: O(1) (constant)
     *
     * Returns the right son of node
     * @return right child node
     */
    public Node<E> getRightChild(){
        return rightChild;
    }
    /**
     * Time complexity: O(1) (constant)
     *
     * Returns the parent of node
     * @return parent node
     */
    public Node<E> getParent(){
        return parent;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns true if node n does not have any children.
     * @return true if is a leaf
     */
    boolean isLeaf() {
        return getLeftChild()== null && getRightChild()==null;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Update the element
     * @param elem element
     */
    public void setElement(E elem) {
        element=elem;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Update the left child
     * @param node new left child node
     */
    public void setLeftChild(Node<E> node) {
        leftChild=node;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Update the right child
     * @param node new right child node
     */
    public void setRightChild(Node<E> node) {
        rightChild=node;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Update the parent
     * @param node new parent node
     */
    public void setParent(Node<E> node) {
        parent=node;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns true if is the root
     */
    boolean isRoot() {
        return getParent()==null;
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * Returns the height of the subtree rooted at this node.
     */
    public int getHeight() {
        if (isLeaf())
            return 0;
        if (leftChild==null)
            return 1 + ((BTNode<E>)rightChild).getHeight();
        if (rightChild==null)
            return 1 + ((BTNode<E>)leftChild).getHeight();
        return 1 + Math.max(((BTNode<E>)rightChild).getHeight(), ((BTNode<E>)leftChild).getHeight());
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * @return node further on the left
     */
    BTNode<E> furtherLeftElement() {
        if (leftChild==null)
            return this;
        BTNode<E> left = (BTNode<E>) leftChild;
        while (left.getLeftChild() != null) {
            left = (BTNode<E>) left.getLeftChild();
        }
        return left;
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * @return node further on the right
     */
    BTNode<E> furtherRightElement() {
        if (rightChild==null)
            return this;
        BTNode<E> right = (BTNode<E>) rightChild;
        while (right.getRightChild() != null) {
            right = (BTNode<E>) right.getRightChild();
        }
        return right;
    }


    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(element);
        oos.writeObject(parent);
        oos.writeObject(leftChild);
        oos.writeObject(rightChild);
        oos.flush();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        @SuppressWarnings("unchecked")
        E element = (E) ois.readObject();
        this.element = element;
        @SuppressWarnings("unchecked")
        BTNode<E> parent = (BTNode<E>) ois.readObject();
        this.parent = parent;
        @SuppressWarnings("unchecked")
        BTNode<E> leftChild = (BTNode<E>) ois.readObject();
        this.leftChild = leftChild;
        @SuppressWarnings("unchecked")
        BTNode<E> rightChild = (BTNode<E>) ois.readObject();
        this.rightChild = rightChild;

    }

}
