package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * Closed Hash Table
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    @Serial
    private static final long serialVersionUID = 1L;


    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;
    static final int GROWTH_FACTOR =2;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    /**
     * Array of entries
     */
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        // Compiler gives a warning.
        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    /**
     * Methods for handling collisions.
     * Returns the hash value of the specified key.
     */
    int hash( K key, int i ){
        return Math.abs( key.hashCode() + i) % table.length;
    }

    /**
     * Linear Proving
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        int idx = NOT_FOUND;
        int i = 0;
        while (idx == NOT_FOUND && i < table.length) {
            int hash = hash(key,i);
            if (table[hash(key,i)] != null && table[hash(key,i)].key().equals(key))
                idx = hash;
            else
                i++;
        }
        return idx;
    }
    
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        int index = searchLinearProving(key);
        if (index == NOT_FOUND)
            return null;
        return table[searchLinearProving(key)].value();
    }

    /**
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
        if (isFull())
            rehash();
        int idx = hash(key,0);
        Entry<K,V> oldValue = table[idx];
        int i = 0;
        while (oldValue != null && (!oldValue.key().equals(key) || oldValue.key() != key) && i < table.length) {
            i++;
            idx = hash(key,i);
            oldValue = table[idx];
        }
        table[idx] = new Entry<>(key,value);
        if (oldValue != null)
            return oldValue.value();
        currentSize++;
        return null;
    }

     private void rehash(){
         Entry<K,V>[] oldTable = table;

         int newCapacity = nextPrime(table.length * GROWTH_FACTOR);
         table = (Entry<K,V>[]) new Entry[newCapacity];

         maxSize = (int) (newCapacity * MAX_LOAD_FACTOR);

         for (Entry<K, V> entry : oldTable) {
             if (entry != null && entry != REMOVED_CELL) {
                 int idx = searchLinearProving(entry.key());
                 table[idx] = entry;
             }
         }
     }

   
    /**
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
        int idx = searchLinearProving(key);
        V oldValue = table[idx].value();
        if (oldValue == null)
            return null;
        table[idx] = (Entry<K, V>) REMOVED_CELL;
        currentSize--;
        return oldValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new ClosedHashIterator<>(table, currentSize);
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(currentSize);
        oos.writeInt(maxSize);
        oos.writeInt(table.length);
        for(int i = 0; i < table.length; i++){
            oos.writeObject(table[i]);
        }
        oos.flush();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        currentSize = ois.readInt();
        maxSize = ois.readInt();
        int tableSize = ois.readInt();
        table = (Entry<K,V>[]) new Entry[tableSize];
        for (int i = 0; i < tableSize; i++){
            table[i] = (Entry<K, V>) ois.readObject();
        }
    }

}
