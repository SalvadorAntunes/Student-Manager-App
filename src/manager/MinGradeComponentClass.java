package manager;

import java.io.Serial;

/**
 * Represents a component that has a minimum grade for subject approval
 *
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public class MinGradeComponentClass extends ComponentClass implements MinGradeComponent {

    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Component's minimum grade
     */
    private final int minGrade;

    /**
     * Is component's approval is mandatory for subject approval
     */
    private final boolean attendance;

    public MinGradeComponentClass(String name, int weight, int assignmentNumber, int minGrade, boolean attendance) {
        super(name, weight, assignmentNumber);
        this.minGrade = minGrade;
        this.attendance = attendance;
    }

    @Override
    public boolean hasPassed(double grade) {
        return Math.toIntExact(Math.round(grade)) < minGrade;
    }

    @Override
    public boolean mandatoryForAttendance() {
        return attendance;
    }
}
