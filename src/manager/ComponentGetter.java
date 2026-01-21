package manager;

import dataStructures.Iterator;

import java.io.Serializable;

/**
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public interface ComponentGetter extends Serializable {

    /**
     * Gets the component's name
     * @return component's name
     */
    String getName();

    /**
     * Gets the component's weight in the subject
     * @return component's weight in the subject
     */
    int getWeight();

    /**
     * Gets the number of assignment in the component
     * @return the number of assignment in the component
     */
    int getAssignmentNumber();

    /**
     * Gets the component's assignments
     * @return iterator with the component's assignments
     */
    Iterator<Assignment> getAssignments();

}
