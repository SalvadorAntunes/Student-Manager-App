package dataStructures;

/**
 * Iterator Abstract Data Type with Filter
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 * @param <E> Generic Element
 *
 */
public interface Predicate<E> {
    /**
     *  Filter that an element needs to check
     * @param elem
     * @return
     */
    boolean check(E elem);
}
