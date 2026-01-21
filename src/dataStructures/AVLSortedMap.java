package dataStructures;

/**
 * AVL Tree Sorted Map
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{

    /**
     * Time complexity: O(n) (linear)
     *
     * Adds a new node into the tree
     * @param key key
     * @param value value
     * @return null if it is a new node or the oldvalue
     */
    public V put(K key, V value) {
        Entry<K,V> entry = new Entry<>(key,value);
        AVLNode<Entry<K,V>> newNode = new AVLNode<>(entry);
        V oldValue = addNode(newNode);
        if (oldValue != null)
            return oldValue;
        AVLNode<Entry<K,V>> z = (AVLNode<Entry<K, V>>) newNode.getParent();
        rebalance(z);
        return null;
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * Removes a node of the tree
     * @param key whose entry is to be removed from the map
     * @return value of the removed node or null
     */
    public V remove(K key) {
        AVLNode<Entry<K,V>> node= (AVLNode<Entry<K, V>>) getNode((BTNode<Entry<K, V>>) root, key);
        if (node==null)
            return null;
        AVLNode<Entry<K,V>> parent = (AVLNode<Entry<K,V>>)node.getParent();
        super.remove(key);
        if (parent!=null)
            rebalance(parent);
        else
            rebalance((AVLNode<Entry<K, V>>) root);
        return node.getElement().value();
    }

    /**
     *  Time complexity: O(n) (linear)
     *
     * Rebalance the tree
     * @param z root of the subtree
     */
    private void rebalance(AVLNode<Entry<K,V>> z) {
        if (z != null){
            boolean found = false;
            do{
                AVLNode<Entry<K,V>> parent = null;
                int parentHeight =0;
                if (!z.isRoot()){
                    parent = (AVLNode<Entry<K, V>>) z.getParent();
                    parentHeight = parent.getHeight();
                } else
                    found = true;
                z.updateHeight();
                if (!z.isBalanced()){
                    rebalanceAndUpdateHeight(z);
                }
                if (parent != null){
                    if (parentHeight == parent.updateHeight())
                        found = true;
                    else
                        z = parent;
                }
            } while (!found);
        }
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * Makes the rebalence and updates the heights of the nodes
     * @param z root of the subtree
     */
    private void rebalanceAndUpdateHeight(AVLNode<Entry<K,V>> z) {
        AVLNode<Entry<K,V>> y = getBiggerTree(z);
        AVLNode<Entry<K,V>> x = getBiggerTree(y);
        AVLNode<Entry<K,V>> newRoot = (AVLNode<Entry<K, V>>) restructure(x);
        if (newRoot == y){
            x.updateHeight();
            z.updateHeight();
            y.updateHeight();
        } else {
            z.updateHeight();
            y.updateHeight();
            x.updateHeight();
        }
    }

    /**
     * Time complexity: O(n) (linear)
     *
     * @param z root of the subtree
     * @return the child of the subtree
     */
    private AVLNode<Entry<K,V>> getBiggerTree(AVLNode<Entry<K,V>> z){
        if (z == null || z.getLeftChild() == null && z.getRightChild() == null)
            return null;
        if (z.getLeftChild() == null && z.getRightChild() != null)
            return (AVLNode<Entry<K, V>>) z.getRightChild();
        if (z.getRightChild() == null && z.getLeftChild() != null)
            return (AVLNode<Entry<K, V>>) z.getLeftChild();
        AVLNode<Entry<K,V>> y;
        if (((AVLNode<Entry<K,V>>)z.getLeftChild()).getHeight() >
                ((AVLNode<Entry<K,V>>)z.getRightChild()).getHeight())
            y = (AVLNode<Entry<K,V>>)z.getLeftChild();
        else
            y = (AVLNode<Entry<K,V>>)z.getRightChild();
        return y;
    }
}