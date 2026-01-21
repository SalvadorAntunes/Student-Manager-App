package manager;

import java.io.Serializable;

/**
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public interface Student extends StudentGetter, Serializable {

    /**
     * Grade's an assignment
     * @param grade grade
     * @param assignment assignment
     */
    void setGrade(double grade, Assignment assignment);
}
