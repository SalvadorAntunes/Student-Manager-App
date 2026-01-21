package manager;

import java.io.Serializable;

/**
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public interface StudentGetter extends Serializable {

    /**
     * Gets the student's name
     * @return student's name
     */
    String getName();

    /**
     * Gets the student's number
     * @return student's number
     */
    int getNumber();

    /**
     * Gets the student's final grade
     * @return student's final grade
     */
    double getFinalGrade();

    /**
     * Checks if the student has at least on assignment completed
     * @return true if the student has at least on assignment completed, false otherwise
     */
    boolean hasGrades();

    /**
     * Gets the student's grade in a given assignment
     * @param assignment assignment
     * @return student's grade in the given assignment
     */
    double getAssignmentGrade(Assignment assignment);

    /**
     * Gets the student's grade in a given component
     * @param component component
     * @return student's grade in the given component
     */
    double getComponentGrade(ComponentGetter component);

    /**
     * Checks if the student has attendance
     * To have attendance he must pass every component mandatory for attendance
     * @return if the student has attendance, else otherwise
     */
    boolean hasAttendance();

    /**
     * Checks if the student has approved in the subject
     * To be approved he must pass every component and his final grade must be above 10
     * @return if the student has approved in the subject, false otherwise
     */
    boolean hasPassed();

}
