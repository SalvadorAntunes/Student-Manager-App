package manager;

import java.io.Serializable;

/**
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public interface Assignment extends Serializable {

    /**
     * Gets the assignment's name
     * @return assignment's name
     */
    String name();

    /**
     * Gets the assignment's weight in the component
     * @return the assignment's weight in the component
     */
    int weight();

    /**
     * Gets the assignment's component
     * @return the assignment's component
     */
    ComponentGetter component();

}
