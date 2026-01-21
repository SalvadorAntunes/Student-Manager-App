package dataStructures;

/**
 * Advanced Binary Search Tree
 *
 * @author @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
abstract class AdvancedBSTree <K extends Comparable<K>,V> extends BSTSortedMap<K,V>{
    
    /**
     * Time complexity: O(1) (constant)
     *
 	* Performs a single left rotation rooted at z node.
 	* Node y was a  right  child  of z before the  rotation,
 	* then z becomes the left child of y after the rotation.
 	* @param z - root of the rotation
     *@pre: z has a right child
 	*/
	protected void rotateLeft( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) z.getRightChild();
        BTNode<Entry<K,V>> x = (BTNode<Entry<K,V>>) y.getRightChild();
        handleParents(z, y);
        z.setParent(y);
        if (x != null)
            x.setParent(y);
        BTNode<Entry<K,V>> yChild = (BTNode<Entry<K,V>>) y.getLeftChild();
        z.setRightChild(yChild);
        if (yChild != null)
            yChild.setParent(z);
        y.setLeftChild(z);
        y.setRightChild(x);
	}

     /**
      * Time complexity: O(1) (constant)
      *
     * Performs a single right rotation rooted at z node.
     * Node y was a left  child  of z before the  rotation,
     * then z becomes the right child of y after the rotation.
     * @param z - root of the rotation
     * @pre: z has a left child
     */
    protected void rotateRight( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) z.getLeftChild();
        BTNode<Entry<K,V>> x = (BTNode<Entry<K,V>>) y.getLeftChild();
        handleParents(z, y);
        z.setParent(y);
        if (x != null)
            x.setParent(y);
        BTNode<Entry<K,V>> yChild = (BTNode<Entry<K,V>>) y.getRightChild();
        z.setLeftChild(yChild);
        if (yChild != null)
            yChild.setParent(z);
        y.setRightChild(z);
        y.setLeftChild(x);
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     *
     * @param x - root of the rotation
     * @return the new root of the restructured subtree
     */
    protected BTNode<Entry<K,V>> restructure (BTNode<Entry<K,V>> x) {
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) x.getParent();
        BTNode<Entry<K,V>> z = (BTNode<Entry<K,V>>) y.getParent();
        if (y == z.getLeftChild()){
            if (x == y.getRightChild())
                rotateLeft(y);
            rotateRight(z);
            if (x == y.getRightChild())
                return x;
        } else {
            if (x == y.getLeftChild())
                rotateRight(y);
            rotateLeft(z);
            if (x == y.getLeftChild())
                return x;
        }
        return y;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Handles the parents during the rotations
     * @param z parent of y
     * @param y parent of x
     */
    private void handleParents(BTNode<Entry<K,V>> z, BTNode<Entry<K,V>> y){
        if (z.isRoot()){
            y.setParent(null);
            root = y;
        } else{
            y.setParent(z.getParent());
            BTNode<Entry<K,V>> zParent = (BTNode<Entry<K,V>>) z.getParent();
            if (z == zParent.getLeftChild())
                zParent.setLeftChild(y);
            else
                zParent.setRightChild(y);
        }
    }
}
