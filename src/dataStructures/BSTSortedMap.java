package dataStructures;

import dataStructures.exceptions.EmptyMapException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * Binary Search Tree Sorted Map
 *
 * @author @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V>{

    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }
    /**
     * Time complexity: O(log n)
     *
     * Returns the entry with the largest key in the dictionary.
     * @return the min entry
     * @throws EmptyMapException -> if size = 0
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Time complexity: O(log n)
     *
     * Returns the entry with the largest key in the dictionary.
     * @return the max entry
     * @throws EmptyMapException -> if size = 0
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }


    /**
     * Time complexity: O(log n)
     *
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if(node==null)
            return null;
        int comp = key.compareTo(node.getElement().key());
        if(comp==0){
            return node.getElement().value();
        } else
            return null;
    }

    /**
     * Time complexity: O(log n)
     *
     * @param node node to search
     * @param key key
     * @return if found the node with the specific key or the possible parent
     */
    protected BTNode<Entry<K,V>> getNode(BTNode<Entry<K,V>> node, K key) {
        if(node==null)
            return null;
        int comp = key.compareTo(node.getElement().key());
        if(comp == 0)
            return node;
        else if(comp<0 && node.getLeftChild()!=null)
            return getNode((BTNode<Entry<K,V>>) node.getLeftChild(), key);
        else if (comp>0 && node.getRightChild()!=null)
            return getNode((BTNode<Entry<K,V>>) node.getRightChild(), key);
        else if (comp<0 && node.getLeftChild()==null)
            return node;
        else
            return node;
    }

    /**
     * Time complexity: O(log n)
     *
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        Entry<K,V> entry = new Entry<>(key, value);
        BTNode<Entry<K,V>> newNode = new BTNode<>(entry);
        return addNode(newNode);
    }

    /**
     * Time complexity: O(log n)
     *
     * @param newNode new node to be added or substituted
     * @return null if is a new node or the oldValue
     */
    protected V addNode(BTNode<Entry<K,V>> newNode) {
        Entry<K,V> newEntry = newNode.getElement();
        BTNode<Entry<K,V>> node = getNode((BTNode<Entry<K, V>>) root, newEntry.key());
        if (node==null){
            root = newNode;
        }
        else{
            if (node.getElement().key().equals(newEntry.key())) {
                V oldValue = node.getElement().value();
                node.setElement(newEntry);
                return oldValue;
            }
            if (newEntry.key().compareTo(node.getElement().key()) < 0){
                node.setLeftChild(newNode);
                newNode.setParent(node);
            } else{
                node.setRightChild(newNode);
                newNode.setParent(node);
            }
        }
        currentSize++;
        return null;
    }


    /**
     * Time complexity: O(log n)
     *
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {
        BTNode<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node==null || !node.getElement().key().equals(key))
            return null;
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>)node.getParent();
        if (node.getLeftChild() == null && node.getRightChild() == null)
            removeHasNoChildren(node, parent);
        else if (node.getRightChild() != null && node.getLeftChild() == null)
            removeHasRightChild(node, parent);
        else if (node.getLeftChild() != null && node.getRightChild() == null)
            removeHasLeftChild(node, parent);
        else
            removeHasBothChildren(node, parent);
        currentSize--;
        return node.getElement().value();
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Remove the node with no children
     * @param node node to be removed
     * @param parent parent node
     */
    private void removeHasNoChildren(BTNode<Entry<K,V>> node, BTNode<Entry<K,V>> parent) {
        if (!node.isRoot()){
            if (parent.getLeftChild() == node)
                parent.setLeftChild(null);
            else
                parent.setRightChild(null);
        } else
            root = null;
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Remove the node with a right child
     * @param node node to be removed
     * @param parent parent node
     */
    private void removeHasRightChild(BTNode<Entry<K,V>> node, BTNode<Entry<K,V>> parent) {
        if (!node.isRoot()){
            if (parent.getLeftChild() == node)
                parent.setLeftChild(node.getRightChild());
            else
                parent.setRightChild(node.getRightChild());
            ((BTNode<Entry<K,V>>)node.getRightChild()).setParent(parent);
        } else {
            root = node.getRightChild();
            ((BTNode<Entry<K,V>>) root).setParent(null);
        }
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Remove the node with a left child
     * @param node node to be removed
     * @param parent parent node
     */
    private void removeHasLeftChild(BTNode<Entry<K,V>> node, BTNode<Entry<K,V>> parent) {
        if (!node.isRoot()){
            if (parent.getLeftChild() == node)
                parent.setLeftChild(node.getLeftChild());
            else
                parent.setRightChild(node.getLeftChild());
            ((BTNode<Entry<K,V>>)node.getLeftChild()).setParent(parent);
        } else {
            root = node.getLeftChild();
            ((BTNode<Entry<K,V>>) root).setParent(null);
        }
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Remove the node with 2 children
     * @param node node to be removed
     * @param parent parent node
     */
    private void removeHasBothChildren(BTNode<Entry<K,V>> node, BTNode<Entry<K,V>> parent) {
        BTNode<Entry<K,V>> newNode = ((BTNode<Entry<K,V>>)node.getRightChild()).furtherLeftElement();
        if (newNode == node.getRightChild()){
            ((BTNode<Entry<K,V>>)node.getLeftChild()).setParent(newNode);
            newNode.setLeftChild(node.getLeftChild());
        } else {
            BTNode<Entry<K,V>> rightChild = (BTNode<Entry<K, V>>) newNode.getRightChild();
            ((BTNode<Entry<K,V>>)newNode.getParent()).setLeftChild(rightChild);
            if (rightChild != null)
                rightChild.setParent(newNode.getParent());
            newNode.setLeftChild(node.getLeftChild());
            ((BTNode<Entry<K,V>>) node.getLeftChild()).setParent(newNode);
            newNode.setRightChild(node.getRightChild());
            ((BTNode<Entry<K,V>>) node.getRightChild()).setParent(newNode);
        }
        newNode.setParent(parent);
        if (node.isRoot()) {
            root = newNode;
        } else{
            if (parent.getLeftChild() == node)
                parent.setLeftChild(newNode);
            else
                parent.setRightChild(newNode);
        }
    }

    /**
     * Time complexity: O(1) (constant)
     *
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    /**
     * Time complexity: O(1) (constant)
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Time complexity: O(1) (constant)
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }


    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(currentSize);
        oos.writeObject(root);
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        currentSize = ois.readInt();
        @SuppressWarnings("unchecked")
        BTNode<Entry<K,V>> root = (BTNode<Entry<K,V>>) ois.readObject();
        this.root = root;
    }

}
