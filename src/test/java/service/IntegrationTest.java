package service;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import org.junit.Before;
import org.junit.Test;
import repository.AssignmentRepo;
import repository.GradeRepo;
import repository.StudentRepo;
import validation.AssignmentValidator;
import validation.GradeValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.Assert.assertTrue;

public class IntegrationTest {
    private Service service;

    @Before
    public void setUp() {
        //initiate proper validators
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        //use in memory repositories to avoid the need for clean-up and the possibility for conflicts
        StudentRepo studentRepo = new StudentRepo(studentValidator);
        AssignmentRepo assignmentRepo = new AssignmentRepo(assignmentValidator);
        GradeRepo gradeRepo = new GradeRepo(gradeValidator, studentRepo, assignmentRepo);

        EmailService emailService = new EmailService();

        //create the service; ie the entity to be checked
        service = new Service(studentRepo, assignmentRepo, gradeRepo, emailService);

        //add an entity in the service to allow testing when a student already exists
        service.saveStudent("1", "Bob", 932, "xXxBobBobitzaxXx@gmail.com", "prof");
        service.saveAssignment("1", "Bob", 3, 2);
    }

    @Test
    public void bigBang() {
        service.saveStudent("2", "Bob", 932, "xXxBobBobitzaxXx@gmail.com", "prof");
        service.saveAssignment("2", "Bob", 3, 2);
        assertTrue(service.saveGrade("2", "2", 10, 3, "good job"));
    }

    @Test
    public void saveValidStudentCheckReturnValueTest() {
        assertTrue(service.saveStudent("11", "Andrada", 933, "email", "professor"));
    }

    @Test
    public void saveValidAssignmentCheckReturnValueTest() {
        assertTrue(service.saveAssignment("11", "Andrada", 3, 2));
    }

    @Test
    public void saveValidGradeCheckReturnValueTest() {
        assertTrue(service.saveGrade("1", "1", 10, 3, "good job"));
    }
}
