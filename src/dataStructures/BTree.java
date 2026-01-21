package dataStructures;

/**
 * Binary Tree
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 */
abstract class BTree<E> extends Tree<E> {

    /**
     * Time complexity: O(n) (linear)
     *
     * Returns the height of the tree.
     */
    public int getHeight() {
        if(isEmpty())
            return 0;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Time complexity: O(log n)
     *
     * @return node further on the left
     */
    BTNode<E> furtherLeftElement() {
        return ((BTNode<E>)root).furtherLeftElement();
    }

    /**
     * Time complexity: O(log n)
     *
     * @return node further on the right
     */
    BTNode<E> furtherRightElement() {
        return ((BTNode<E>)root).furtherRightElement();
    }

}
