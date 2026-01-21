package manager;

import dataStructures.*;
import manager.exceptions.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a subject, stores its students, its components and its assignments
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public class SubjectClass implements Subject, Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Subject's name
     */
    private final String name;

    /**
     * Subject's components
     */
    private final Map<String, Component> components;

    /**
     * Subject's components in insertion order
     */
    private final List<ComponentGetter> componentList;

    /**
     * Subject's assignments
     */
    private final Map<String, Assignment> assignments;

    /**
     * Subject's total weight, after adding all components it must be 100
     */
    private int totalWeight;

    /**
     * Used to get a student by his number
     */
    private final Map<Integer, Student> studentsNumber;

    /**
     * Used to get a student by his name
     */
    private final Map<String, Student> studentsName;

    /**
     * Subject's students by alphabetic order
     */
    private final SortedMap<String, StudentGetter> studentsByAlphabeticOrder;

    /**
     * Subject's students sorted by their number
     */
    private final SortedMap<Integer, StudentGetter> studentsSortedByNumber;


    public SubjectClass() {
        this(null, 0);
    }

    public SubjectClass(String name, int components) {
        totalWeight = 0;
        this.name = name;
        this.componentList = new ListInArray<>(components);
        this.components = new ClosedHashTable<>(components);
        this.assignments = new ClosedHashTable<>(components*2);
        this.studentsNumber = new ClosedHashTable<>();
        this.studentsName = new ClosedHashTable<>();
        this.studentsByAlphabeticOrder = new AVLSortedMap<>();
        this.studentsSortedByNumber = new AVLSortedMap<>();
    }

    @Override
    public boolean isSubjectDefined() {
        return name != null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public StudentGetter getStudent(String student) throws StudentDoesNotExist {
        Student s;
        try {
            int number = Integer.parseInt(student);
            s = studentsNumber.get(number);
        } catch (Exception e) {
            s = studentsName.get(student.toLowerCase());
        }
        if (s == null)
            throw new StudentDoesNotExist();
        return s;
    }

    @Override
    public Iterator<ComponentGetter> getComponents() {
        return componentList.iterator();
    }

    @Override
    public int getComponentCount() {
        return components.size();
    }


    @Override
    public Iterator<StudentGetter> getStudentsByName(Predicate<StudentGetter> predicate){
        return new FilterIterator<>(studentsByAlphabeticOrder.values(), predicate);
    }

    @Override
    public Iterator<StudentGetter> getStudentsById(Predicate<StudentGetter> predicate){
        return new FilterIterator<>(studentsSortedByNumber.values(), predicate);
    }

    @Override
    public Assignment getAssignment(String assignment) throws AssignmentDoesNotExist{
        Assignment a = assignments.get(assignment.toLowerCase());
        if (a == null)
            throw new AssignmentDoesNotExist();
        return a;
    }

    @Override
    public boolean addComponent(String name, int weight, int assignmentNumber, int minGrade, boolean attendance)
            throws ComponentAlreadyExists, InvalidWeight, InvalidAssignments {
        if (components.get(name.toLowerCase()) != null)
            throw new ComponentAlreadyExists();
        if (weight < 1 || weight > 100)
            throw new InvalidWeight();
        if (assignmentNumber < 1)
            throw new InvalidAssignments();
        Component component;
        if (minGrade <= 0 || minGrade > 20)
            component =  new ComponentClass(name, weight, assignmentNumber);
        else
            component = new MinGradeComponentClass(name, weight, assignmentNumber, minGrade, attendance);
        components.put(name.toLowerCase(), component);
        componentList.addLast(component);
        totalWeight += weight;
        return totalWeight == 100;
    }

    @Override
    public boolean addAssignment(String name, int weight, String component)
            throws AssignmentAlreadyExists, InvalidWeight {
        if (assignments.get(name.toLowerCase()) != null)
            throw new AssignmentAlreadyExists();
        if (weight < 1 || weight > 100)
            throw new InvalidWeight();
        Component c = components.get(component.toLowerCase());
        Assignment assignment = new AssignmentClass(name, weight, c);
        assignments.put(name.toLowerCase(), assignment);
        return c.addAssignment(assignment);
    }


    @Override
    public void addStudent(int id, String name) throws StudentAlreadyExists, SubjectNotDefined {
        if (!isSubjectDefined())
            throw new SubjectNotDefined();
        if (studentsNumber.get(id) != null || studentsName.get(name) != null)
            throw new StudentAlreadyExists();
        Student student =  new StudentClass(name, id, components.size(), components.iterator());
        studentsNumber.put(id, student);
        studentsName.put(name.toLowerCase(), student);
        studentsByAlphabeticOrder.put(name, student);
        studentsSortedByNumber.put(id, student);
    }

    @Override
    public void gradeStudent(String id, String assignment, double grade) throws StudentDoesNotExist,
            AssignmentDoesNotExist, InvalidGrade  {
        Student s = (Student) getStudent(id);
        if (s == null)
            throw new StudentDoesNotExist();
        Assignment a = assignments.get(assignment.toLowerCase());
        if (a == null)
            throw new AssignmentDoesNotExist();
        if (grade < 0 || grade > 20)
            throw new InvalidGrade();
        s.setGrade(grade, a);
    }

}
