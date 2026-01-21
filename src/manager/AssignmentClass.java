package manager;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an assignment or any form of evaluation
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public class AssignmentClass implements Assignment, Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Assignment's name
     */
    private final String name;

    /**
     * Assignment's weight in the component
     */
    private final int weight;

    /**
     * Assignment's component
     */
    private final ComponentGetter component;

    public AssignmentClass(String name, int weight, ComponentGetter component) {
        this.component = component;
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int weight() {
        return weight;
    }

    @Override
    public ComponentGetter component() {
        return component;
    }

}
