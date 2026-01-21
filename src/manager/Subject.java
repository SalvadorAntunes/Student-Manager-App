package manager;

import dataStructures.Iterator;
import dataStructures.Predicate;
import manager.exceptions.*;

import java.io.Serializable;

/**
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public interface Subject extends Serializable {

    /**
     * Checks is the current subject is defined
     * @return true is the current subject is defined, false otherwise
     */
    boolean isSubjectDefined();

    /**
     * Gets the subject's name
     * @return the subject's name
     */
    String getName();

    /**
     * Gets a student registered in this subject
     * @param student student's name
     * @return requested student
     * @throws StudentDoesNotExist if there is not a student with the requested name
     */
    StudentGetter getStudent(String student) throws StudentDoesNotExist;

    /**
     * Gets the subject's components
     * @return Iterator with the subject's components
     */
    Iterator<ComponentGetter> getComponents();

    /**
     * Gets the number of components in the subject
     * @return number of components in the subject
     */
    int getComponentCount();

    /**
     * Gets students by alphabetic order
     * @param predicate filter
     * @return student iterator by alphabetic order
     */
    Iterator<StudentGetter> getStudentsByName(Predicate<StudentGetter> predicate) ;

    /**
     * Gets students sorted by their id
     * @param predicate filter
     * @return student iterator sorted by their id
     */
    Iterator<StudentGetter> getStudentsById(Predicate<StudentGetter> predicate) ;

    /**
     * Gets an assignment by its name
     * @param assignment assignment name
     * @return assignment with requested name
     * @throws AssignmentDoesNotExist if there's no assignment with that name
     */
    Assignment getAssignment(String assignment) throws AssignmentDoesNotExist;

    /**
     * Adds a component to the subject
     * @param name component's name
     * @param weight component's weight
     * @param assignmentNumber number of assignments in the component
     * @param minGrade minimum grade in the component for approval
     * @param attendance must pass component for attendance
     * @return true if the subject's total weight is 100, false otherwise
     * @throws ComponentAlreadyExists if there is a component with the same name
     * @throws InvalidWeight if the component's weight is lower than 1 other greater than 100
     * @throws InvalidAssignments if the number of assignments is lower than 1
     */
    boolean addComponent(String name, int weight, int assignmentNumber, int minGrade, boolean attendance)
            throws ComponentAlreadyExists, InvalidWeight, InvalidAssignments;

    /**
     * Adds an assignment to a component
     * @param name assignment's name
     * @param weight assignment's weight in the component
     * @param component component's name
     * @return true if the component's total weight is 100, false otherwise
     * @throws AssignmentAlreadyExists if an assignment with the given name already exists
     * @throws InvalidWeight if the assignment's weight is lower than 1 other greater than 100
     */
    boolean addAssignment(String name, int weight, String component)
            throws AssignmentAlreadyExists, InvalidWeight;

    /**
     * Adds a student to the subject
     * @param number student's number
     * @param name student's name
     * @throws StudentAlreadyExists if there is already a student with the given number or with the given name
     * @throws SubjectNotDefined if the current subject is not defined
     */
    void addStudent(int number, String name) throws StudentAlreadyExists, SubjectNotDefined;

    /**
     * Grades a student's assignment
     * @param id student's number or name
     * @param assignment assignment's name
     * @param grade grade
     * @throws StudentDoesNotExist if there isn't a student with the given number or with the given name
     * @throws AssignmentDoesNotExist if there isn't an assignment with the given name
     * @throws InvalidGrade if the grade is lower than 0 or greater than 20
     */
    void gradeStudent(String id, String assignment, double grade) throws StudentDoesNotExist,
            AssignmentDoesNotExist, InvalidGrade;

}
