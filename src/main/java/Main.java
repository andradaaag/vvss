

import console.*;
import domain.*;
import repository.*;
import service.*;
import validation.*;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentRepo studentRepo = new StudentFileRepo(studentValidator, "students.txt");
        AssignmentRepo assignmentRepo = new AssignmentFileRepo(assignmentValidator, "assignments.txt");
        GradeRepo gradeRepo = new GradeFileRepo(gradeValidator, studentRepo,assignmentRepo,"grades.txt");

        Service service = new Service(studentRepo, assignmentRepo, gradeRepo);
        UI console = new UI(service);
        console.run();

        //PENTRU GUI
        // de avut un check: daca profesorul introduce sau nu saptamana la timp
        // daca se introduce nota la timp, se preia saptamana din sistem
        // altfel, se introduce de la tastatura
    }
}
