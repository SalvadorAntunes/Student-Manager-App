package manager;

import dataStructures.ClosedHashTable;
import dataStructures.Iterator;
import dataStructures.Map;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a student, stores his grades
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public class StudentClass implements Student, Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Student's name
     */
    private final String name;

    /**
     * Student's number
     */
    private final int number;

    /**
     * Number of completed assignments
     */
    private int assignmentNumber;

    /**
     * Student's final grade
     */
    private double finalGrade;

    /**
     * Student's grades
     */
    private final Map<ComponentGetter, Map<Assignment, Double>> grades;

    /**
     * Student's component grades
     */
    private final Map<ComponentGetter, Double> componentGrades;

    public StudentClass(String name, int number,int componentNumber, Iterator<Map.Entry<String, Component>> it) {
        this.name = name;
        this.number = number;
        finalGrade = 0;
        this.assignmentNumber = 0;
        grades = new ClosedHashTable<>(componentNumber);
        componentGrades = new ClosedHashTable<>(componentNumber);
        while (it.hasNext()){
            Map.Entry<String, Component> entry = it.next();
            grades.put(entry.value(), new ClosedHashTable<>());
            componentGrades.put(entry.value(), 0.0);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public double getFinalGrade() {
        return finalGrade;
    }

    @Override
    public boolean hasGrades() {
        return assignmentNumber != 0;
    }

    @Override
    public double getAssignmentGrade(Assignment assignment) {
        if (grades.get(assignment.component()) == null || grades.get(assignment.component()).get(assignment)==null)
            return -1;
        return grades.get(assignment.component()).get(assignment);
    }

    @Override
    public double getComponentGrade(ComponentGetter component) {
        if (componentGrades.get(component) == null)
            return -1;
        return componentGrades.get(component);
    }

    @Override
    public boolean hasAttendance() {
        Iterator<Map.Entry<ComponentGetter, Double>> it = componentGrades.iterator();
        while (it.hasNext()) {
            Map.Entry<ComponentGetter, Double> entry = it.next();
            if (entry.key() instanceof MinGradeComponent && ((MinGradeComponent) entry.key()).hasPassed(entry.value())
                    && ((MinGradeComponent)entry.key()).mandatoryForAttendance())
                return false;
        }
        return true;
    }

    @Override
    public boolean hasPassed() {
        Iterator<Map.Entry<ComponentGetter, Double>> it = componentGrades.iterator();
        while (it.hasNext()) {
            Map.Entry<ComponentGetter, Double> entry = it.next();
            if (entry.key() instanceof MinGradeComponent && ((MinGradeComponent) entry.key()).hasPassed(entry.value()))
                return false;
        }
        return Math.toIntExact(Math.round(finalGrade)) >= 10;
    }

    @Override
    public void setGrade(double grade, Assignment assignment) {
        ComponentGetter component = assignment.component();
        Map<Assignment, Double> assignments = grades.get(component);
        if (assignments.put(assignment, grade) == null)
            assignmentNumber++;
        componentGrades.put(component, calculateComponentGrade(component));
        calculateFinalGrade();

    }

    /**
     * Calculates a student's component grade
     * @param component component
     * @return component grade
     */
    private double calculateComponentGrade(ComponentGetter component) {
        double grade = 0;
        Iterator<Map.Entry<Assignment, Double>> it = grades.get(component).iterator();
        while(it.hasNext()) {
            Map.Entry<Assignment, Double> entry = it.next();
            double assigmentWeight = (double) entry.key().weight() / 100;
            grade += entry.value() * assigmentWeight;
        }
        componentGrades.put(component, grade);
        return grade;
    }

    /**
     * Calculates a student's final grade
     */
    private void calculateFinalGrade() {
        finalGrade = 0;
        Iterator<Map.Entry<ComponentGetter, Double>> it = componentGrades.iterator();
        while(it.hasNext()) {
            Map.Entry<ComponentGetter, Double> entry = it.next();
            finalGrade += entry.value() * ((double) entry.key().getWeight()/100);
        }
    }
}
