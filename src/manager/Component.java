package manager;

import java.io.Serializable;

/**
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public interface Component extends ComponentGetter, Serializable {

    /**
     * Adds an assignment to the component
     * @param assignment assignment
     * @return true if the component's total weight is 100, false otherwise
     */
    boolean addAssignment(Assignment assignment);

}
