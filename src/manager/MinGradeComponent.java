package manager;

/**
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public interface MinGradeComponent extends ComponentGetter{

    /**
     * Checks if a grade is enough for approval in the component
     * @param grade grade
     * @return true if the grade is greater or equal than the minimum grade for approval, false otherwise
     */
    boolean hasPassed(double grade);

    /**
     * Checks if the component's approval is mandatory for subject approval
     * @return true if the component's approval is mandatory for subject approval, false otherwise
     */
    boolean mandatoryForAttendance();
}
