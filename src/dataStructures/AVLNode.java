package dataStructures;

/**
 * AVL Tree Node
 *
 * @author @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {

    /**
     * Height of the node
     */
    protected int height;

    public AVLNode(E elem) {
        super(elem);
        height=0;
    }

    public AVLNode( E element, AVLNode<E> parent,
                    AVLNode<E> left, AVLNode<E> right ){
        super(element, parent, left, right);
        height = Math.max(height(left), height(right));
    }
    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        height= 0;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * @param no node
     * @return height of node
     */
    private int height(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }
    public int getHeight() {
        return height;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Update the left child and height
     * @param node new left child
     */
    public void setLeftChild(AVLNode<E> node) {
        super.setLeftChild(node);
        updateHeight();
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Update the right child and height
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        super.setRightChild(node);
        updateHeight();
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Updates the height of the node
     * @return new height
     */
    public int updateHeight() {
        if (getRightChild()==null && getLeftChild()==null)
            height = 0;
        else {
            AVLNode<E> rightChild = (AVLNode<E>)getRightChild();
            AVLNode<E> leftChild = (AVLNode<E>)getLeftChild();
            int rightHeight = height(rightChild);
            int leftHeight = height(leftChild);
            height = 1 + Math.max(leftHeight, rightHeight);
        }
        return height;
    }

    /**
     * Checks if the node is balanced
     *
     * @return true if is balanced
     */
    public boolean isBalanced() {
        return Math.abs(height((AVLNode<E>)getLeftChild())-height((AVLNode<E>)getRightChild())) <= 1;
    }


}