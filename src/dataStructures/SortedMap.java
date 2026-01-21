package dataStructures;

/**
 * Ordered Dictionary interface
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <K> Generic type Key, must extend comparable
 * @param <V> Generic type Value 
 */
public interface SortedMap<K extends Comparable<K>, V>
        extends Map<K,V> {

    /**
     * Returns the entry with the smallest key in the dictionary.
     */
    Entry<K,V> minEntry( );

    /**
     * Returns the entry with the largest key in the dictionary.
     */
    Entry<K,V> maxEntry( );

} 

