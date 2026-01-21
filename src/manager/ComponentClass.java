package manager;

import dataStructures.*;


import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a component, part of the subject's evaluation
 * Stores its assignments
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public class ComponentClass implements Component, Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Component's name
     */
    private final String name;

    /**
     * Component's weight in the subject
     */
    private final int weight;

    /**
     * Component's total weight
     */
    private int totalWeight;

    /**
     * Component's assignments in insertion order
     */
    private final List<Assignment> assignmentsOrder;

    /**
     * Component's assignments
     */
    private final Map<String, Assignment> assignments;

    public ComponentClass(String name, int weight, int assignmentNumber) {
        this.name = name;
        this.weight = weight;
        totalWeight = 0;
        this.assignmentsOrder = new ListInArray<>(assignmentNumber);
        this.assignments = new ClosedHashTable<>(assignmentNumber);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getAssignmentNumber() {
        return assignments.size();
    }

    @Override
    public Iterator<Assignment> getAssignments() {
        return assignmentsOrder.iterator();
    }

    @Override
    public boolean addAssignment(Assignment assignment) {
        assignments.put(assignment.name().toLowerCase(), assignment);
        assignmentsOrder.addLast(assignment);
        totalWeight += assignment.weight();
        return totalWeight == 100;
    }

}
