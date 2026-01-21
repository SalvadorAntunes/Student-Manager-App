import dataStructures.*;
import manager.*;
import manager.exceptions.*;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Salvador Lourenço Antunes salvadorantunes06@gmail.com
 */
public class Main {

    private static final String FILE_TYPE = ".ser";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        commandInterpreter(in);
        in.close();
    }

    /**
     * Reads the input.
     * @param scanner Scanner
     * @return command to execute
     */
    private static Commands getCommand(Scanner scanner) {
        try {
            String command = scanner.next().toUpperCase().trim();
            return Commands.valueOf(command);
        } catch (IllegalArgumentException e) {
            return Commands.UNKNOWN;
        }
    }


    /**
     * Command Interpreter, reads the input and executes the commands.
     * @param in Scanner
     */
    public static void commandInterpreter(Scanner in) {
        Commands commands;
        Subject subject = new SubjectClass();
        do{
            commands = getCommand(in);
            switch (commands){
                case HELP -> executeHelp(in);
                case EXIT -> executeExit(in, subject);
                case SUBJECT -> subject = executeSubject(in, subject);
                case SAVE -> executeSave(subject);
                case LOAD -> subject = executeLoad(in, subject);
                case STUDENT -> executeStudent(in, subject);
                case GRADE -> executeGrade(in, subject);
                case CHECK -> executeCheck(in, subject);
                case LIST -> executeList(in, subject);
                case EXAM -> executeExam(in, subject);
                case UNKNOWN -> System.out.println("Unknown command! Type 'help' for a list of commands.");
            }

        } while (!commands.equals(Commands.EXIT));
    }

    /**
     * Reads an integer and moves to the next line
     * @param in Scanner
     * @return input
     */
    private static int intNextLine(Scanner in) {
        int number = in.nextInt();
        in.nextLine();
        return number;
    }

    /**
     * Prints commands' functions and their usage
     * @param in Scanner
     */
    private static void executeHelp(Scanner in) {
        in.nextLine();
        for (Commands msg : Commands.values()) {
            System.out.printf(msg.getDesc());
            System.out.println("Usage: " + msg.getUsage());
        }
    }

    /**
     * Saves the current subject and exits the program.
     * @param in Scanner
     * @param subject Program manager
     */
    private static void executeExit(Scanner in, Subject subject) {
        in.nextLine();
        try {
            writeToFile(subject);
        } catch (IOException e) {
            System.out.println("Printing error!");
        }
        System.out.println("Exiting program...");
    }

    /**
     * Defines a new subject. If a current subject is defined, it will be saved to a file
     * @param in Scanner
     * @param subject Program manager
     */
    private static Subject executeSubject(Scanner in, Subject subject) {
        int components;
        String name;
        try {
            components = in.nextInt();
            name = in.nextLine().trim();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.println("Usage: "+ Commands.SUBJECT.getUsage());
            return subject;
        }
        if (subject.isSubjectDefined()){
            try {
                writeToFile(subject);
            } catch (IOException e) {
                System.out.println("Printing error!");
            }
        }
        Subject newSubject = buildSubject(in, name, components);
        if (newSubject == null)
            return subject;
        return newSubject;
    }

    /**
     * Builds a new subject and its evaluation method
     * @param in Scanner
     * @param name Subject name
     * @param components number o components
     * @return the newly build subject
     */
    private static Subject buildSubject(Scanner in, String name, int components) {
        Subject newSubject = new SubjectClass(name, components);
        boolean totalWeight = false;
        for (int i = 0; i < components; i++){
            if (components == 1)
                System.out.print("Enter component name: ");
            else
                System.out.printf("Enter component %d name: ",i+1);
            String component = in.nextLine().trim();
            System.out.print("Is this component mandatory for attendance? Y/N: ");
            boolean mandatory = in.nextLine().trim().equals("Y");
            int weight;
            if (components > 1){
                System.out.print("How much does this component weight? [0; 100]: ");
                weight = intNextLine(in);
            }else weight = 100;
            System.out.print("How many assignments does this component have? : ");
            int assignmentsNr = intNextLine(in);
            System.out.print("What is this component minimum grade? [0; 20]: ");
            int minGrade = intNextLine(in);
            try{
                totalWeight = newSubject.addComponent(component, weight, assignmentsNr,minGrade, mandatory);
            } catch (ComponentAlreadyExists e) {
                System.out.printf("Component %s already exists!\n", component);
                return null;
            }catch (InvalidWeight e){
                System.out.println("Component weight must be an integer between 1 and 100!");
                return null;
            } catch (InvalidAssignments e){
                System.out.println("Assignment number must be an integer above 0!");
                return null;
            }
            boolean componentWeight = false;
            for (int j = 0; j < assignmentsNr; j++){
                if (assignmentsNr == 1)
                    System.out.print("Enter assignment name: ");
                else
                    System.out.printf("Enter assignment %d name: ",j+1);
                String assignmentName = in.nextLine().trim();
                int assignmentWeight;
                if (assignmentsNr > 1){
                    System.out.print("How much does this assignment weight in the component? [0; 100]: ");
                    assignmentWeight = intNextLine(in);
                } else assignmentWeight = 100;
                try {
                    componentWeight = newSubject.addAssignment(assignmentName, assignmentWeight, component);
                } catch (AssignmentAlreadyExists e) {
                    System.out.printf("Assignment %s already exists!\n", assignmentName);
                    return null;
                } catch (InvalidWeight e) {
                    System.out.println("Assignment weight must be an integer between 1 and 100!");
                    return null;
                }
            }
            if (!componentWeight){
                System.out.println("All assignments weight must add up to 100!");
                return null;
            }
        }
        if (!totalWeight){
            System.out.println("All components weight must add up to 100!");
            return null;
        }
        System.out.printf("Subject %s has been created.\n", name);
        return newSubject;
    }


    /**
     * Saves the current subject to a file
     * @param subject Program manager
     */
    private static void executeSave(Subject subject) {
        try {
            writeToFile(subject);
            System.out.printf("Subject %s has been saved.\n", subject.getName());
        } catch (IOException e) {
            System.out.println("Printing error!");
        }
    }

    /**
     * Loads a subject from a file
     * @param in Scanner
     * @param subject Program manager
     */
    private static Subject executeLoad(Scanner in, Subject subject) {
        String subjectName = in.nextLine().trim();
        String fileName = subjectName.toLowerCase() + FILE_TYPE;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Subject deserializedSubject = (Subject) ois.readObject();
            ois.close();
            System.out.printf("Subject %s has been loaded.\n", subjectName);
            return deserializedSubject;
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("Subject %s could not be loaded.\n", subjectName);
        }
        return subject;
    }

    /**
     * Creates a new student
     * @param in Scanner
     * @param subject Current subject
     */
    private static void executeStudent(Scanner in, Subject subject) {
        try {
            int number = in.nextInt();
            String name = in.nextLine().trim();
            try{
                subject.addStudent(number, name);
                System.out.printf("Student %s has been added.\n", name);
            } catch (StudentAlreadyExists e) {
                System.out.printf("Student %s already exists!\n", name);
            } catch (SubjectNotDefined e) {
                System.out.println("Subject not defined!");
            }
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.println("Usage: "+ Commands.STUDENT.getUsage());
        }
    }

    /**
     * Grades a student's assignment
     * @param in Scanner
     * @param subject Current Subject
     */
    private static void executeGrade(Scanner in, Subject subject) {
        String id = in.nextLine().trim();
        if (subject.isSubjectDefined()){
            System.out.print("Assignment name: ");
            String assignment = in.nextLine().trim();
            System.out.print("Assignment grade: ");
            try{
                double grade = in.nextDouble();
                in.nextLine();
                subject.gradeStudent(id, assignment, grade);
                System.out.println("The grade has been submitted.");
            } catch (StudentDoesNotExist e) {
                System.out.printf("Student %s does not exist!\n", id);
            } catch (InvalidGrade e) {
                System.out.println("Grades must range from 0 to 20!");
            } catch (InputMismatchException e ) {
                in.nextLine();
                System.out.println("Grades must range from 0 to 20!");
            } catch (AssignmentDoesNotExist e){
                System.out.printf("Assignment %s does not exist!\n", assignment);
            }
        }else
            System.out.println("Subject not defined!");
    }

    /**
     * Prints a student's assignment grades
     * @param in Scanner
     * @param subject Current subject
     */
    private static void executeCheck(Scanner in, Subject subject) {
        String studentId = in.nextLine().trim();
        if (subject.isSubjectDefined()){
            try{
                StudentGetter student = subject.getStudent(studentId);
                if (!student.hasGrades())
                    System.out.println("No grades.");
                else {
                    System.out.printf("Printing (%d) %s grades:\n", student.getNumber(), student.getName());
                    Iterator<ComponentGetter> it1 = subject.getComponents();
                    while (it1.hasNext()) {
                        ComponentGetter c = it1.next();
                        Iterator<Assignment> it2 = c.getAssignments();
                        while (it2.hasNext()) {
                            Assignment a = it2.next();
                            double grade = student.getAssignmentGrade(a);
                            if (grade != -1)
                                System.out.printf("%s: %.2f\n", a.name(), student.getAssignmentGrade(a));
                        }
                    }
                    if (student.hasAttendance()){
                        double grade = student.getFinalGrade();
                        System.out.printf("Final grade: %d (%.2f)\n", Math.round(grade), grade);
                    } else
                        System.out.println("No attendance yet.");
                }
            } catch (StudentDoesNotExist e){
                if (studentId.isEmpty())
                    System.out.println("Usage: "+ Commands.CHECK.getUsage());
                else
                    System.out.printf("Student %s does not exist!\n", studentId);
            }
        } else
            System.out.println("Subject not defined!");
    }

    /**
     * Executes command List and analyzes the input
     * @param in Scanner in
     * @param subject Current subject
     */
    private static void executeList(Scanner in, Subject subject) {
        String input = in.nextLine().trim();
        if (subject.isSubjectDefined()){
            if (input.isEmpty())
                printFullSheet(subject, in);
            else
                printAssignmentGrades(subject, input, in);
        }else
            System.out.println("Subject not defined!");
    }

    /**
     * Prints the full grade sheet with every component's grade and the final grade
     * @param subject Current subject
     * @param in Scanner
     */
    private static void printFullSheet(Subject subject, Scanner in) {

        Iterator<StudentGetter> itStudents = getStudentIterator(in, subject, p->true);

        if (!itStudents.hasNext())
            System.out.println("There are no students.");
        else {
            List<Object> list = new SinglyLinkedList<>();

            Iterator<ComponentGetter> it1 = subject.getComponents();
            while (it1.hasNext()) {
                ComponentGetter c = it1.next();
                if (c.getAssignmentNumber() > 1) {
                    Iterator<Assignment> it2 = c.getAssignments();
                    while (it2.hasNext())
                        list.addLast(it2.next());
                }
                if (subject.getComponentCount() > 1)
                    list.addLast(c);
            }
            Iterator<Object> listIt = list.iterator();
            System.out.print("Number Name ");
            while (listIt.hasNext()) {
                Object o = listIt.next();
                if (o instanceof ComponentGetter)
                    System.out.printf("%s ", ((ComponentGetter) o).getName());
                else if (o instanceof Assignment)
                    System.out.printf("%s ", ((Assignment) o).name());
            }
            System.out.println("Final Grade");
            listIt.rewind();
            while (itStudents.hasNext()) {
                boolean approval = true;
                boolean attendance = true;
                StudentGetter s = itStudents.next();
                System.out.printf("%d %s ", s.getNumber(), s.getName());
                while (listIt.hasNext()) {
                    Object o = listIt.next();
                    double grade = 0;
                    if (o instanceof ComponentGetter) {
                        grade = s.getComponentGrade((ComponentGetter) o);
                        if (o instanceof MinGradeComponent && ((MinGradeComponent) o).hasPassed(grade)){
                            approval = false;
                            if (((MinGradeComponent)o).mandatoryForAttendance())
                                attendance = false;
                        }
                    } else if (o instanceof Assignment)
                        grade = s.getAssignmentGrade((Assignment) o);
                    if (grade != -1)
                        System.out.printf("%.2f ", grade);
                    else
                        System.out.printf("%d ", 0);
                }
                int finalGrade = Math.toIntExact(Math.round(s.getFinalGrade()));
                if (!attendance)
                    System.out.println("N");
                else if (!approval)
                    System.out.println("F");
                else
                    System.out.println(finalGrade);
                listIt.rewind();
            }
            System.out.println("F - No Approval");
            System.out.println("N - No Attendance");
        }
    }

    /**
     * Prints every student's result in a given assignment
     * @param subject Current Subject
     * @param assignment Assignment's name
     * @param in Scanner
     */
    private static void printAssignmentGrades(Subject subject, String assignment, Scanner in) {

        try {
            Assignment a = subject.getAssignment(assignment);
            Predicate<StudentGetter> predicate = p-> p.getAssignmentGrade(a) != -1;
            Iterator<StudentGetter> it = getStudentIterator(in, subject, predicate);
            if (!it.hasNext())
                System.out.println("No students completed this assignment.");
            while (it.hasNext()){
                StudentGetter s = it.next();
                System.out.printf("%d %s %.2f\n", s.getNumber(), s.getName(), s.getAssignmentGrade(a));
            }
        } catch (AssignmentDoesNotExist e) {
            System.out.printf("Assignment %s does not exist!\n", assignment);
        }

    }

    /**
     * Prints every student automatically registered for the exam.
     * A student is registered automatically if he was not approved but had attendance.
     * @param in Scanner
     * @param subject Current subject
     */
    private static void executeExam(Scanner in, Subject subject)  {
        in.nextLine();
        if (subject.isSubjectDefined()){
            Predicate<StudentGetter> predicate = p->!p.hasPassed() && p.hasAttendance();
            Iterator<StudentGetter> it = getStudentIterator(in, subject, predicate);
            if (!it.hasNext())
                System.out.println("No students registered for the exam.");
            while (it.hasNext()){
                StudentGetter s = it.next();
                if (s.hasAttendance() && !s.hasPassed())
                    System.out.printf("%d %s\n", s.getNumber(), s.getName());
            }
        } else
            System.out.println("Subject not defined!");
    }

    /**
     * Gets a student iterator either by alphabetic order or by their name
     * @param in Scanner
     * @param subject Current subject
     * @param predicate Filter
     * @return requested iterator
     */
    private static Iterator<StudentGetter> getStudentIterator(Scanner in, Subject subject, Predicate<StudentGetter> predicate) {
        Iterator<StudentGetter> it = null;
        System.out.print("Do you prefer the sheet sorted by student number or by alphabetic order? N/A: ");
        do{
            switch (in.nextLine().toUpperCase().trim()){
                case "N", "NUMBER" -> it = subject.getStudentsById(predicate);
                case "A", "NAME", "ALPHABETIC" -> it = subject.getStudentsByName(predicate);
                default -> System.out.print("For number order write n or number, for alphabetic order write a, name or alphabetic: ");
            }
        } while (it == null);
        return it;
    }

    /**
     * Writes the current subject to a file
     * @param subject Program manager
     */
    private static void writeToFile(Subject subject) throws IOException{
        String subjectName = subject.getName();
        String fileName = (subjectName + FILE_TYPE).toLowerCase().replaceAll(" ", "_");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(subject);
        oos.flush();
        oos.close();
    }

}
