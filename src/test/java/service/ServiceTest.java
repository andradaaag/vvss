package service;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import exception.ValidationException;
import org.junit.Before;
import org.junit.Test;
import repository.AssignmentRepo;
import repository.GradeRepo;
import repository.StudentRepo;
import validation.AssignmentValidator;
import validation.GradeValidator;
import validation.StudentValidator;
import validation.Validator;

import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class ServiceTest {
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
    public void saveStudentWithExistingIDTest() {
        assertFalse(service.saveStudent("1", "Bill", 933, "email", "professor"));
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithEmptyNameTest() {
        service.saveStudent("11", "", 933, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithNullNameTest() {
        service.saveStudent("11", null, 933, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithEmptyIdTest() {
        service.saveStudent("", "Bill", 933, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithNullIdTest() {
        service.saveStudent(null, "Bill", 933, "email", "professor");
    }

    // START TESTS FOR EDGE CASES GROUP

    @Test(expected = ValidationException.class)
    public void saveStudentWithGroupNumberOutsideLowerLimitTest() {
        service.saveStudent("11", "Bill", 110, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithGroupNumberAboveLowerLimitTest() {
        service.saveStudent("11", "Bill", 938, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithGroupNumber0Test() {
        assertFalse(service.saveStudent("11", "Bill", 0, "email", "professor"));
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithGroupNumberNegativeTest() {
        service.saveStudent("11", "Bill", -1, "email", "professor");
    }

    @Test
    public void saveStudentWithGroupNumberOnLowerLimitTest() {
        assertTrue(service.saveStudent("11", "Bill", 111, "email", "professor"));
    }

    @Test
    public void saveStudentWithGroupNumberOnHighestLimitTest() {
        assertTrue(service.saveStudent("11", "Bill", 937, "email", "professor"));
    }

    // END TESTS FOR EDGE CASES GROUP

    @Test
    public void saveValidStudentCheckReturnValueTest() {
        assertTrue(service.saveStudent("11", "Andrada", 933, "email", "professor"));
    }

    @Test
    public void saveValidStudentCheckAddEffectTest() {
        long numberOfStudents = StreamSupport.stream(service.getAllStudents().spliterator(), false).count();
        service.saveStudent("11", "Andrada", 933, "email", "professor");
        assertEquals(numberOfStudents + 1, StreamSupport.stream(service.getAllStudents().spliterator(), false).count());
    }

    // TESTS FOR ADD ASSIGNMENT

    @Test
    public void saveValidAssignmentCheckReturnValueTest() {
        assertTrue(service.saveAssignment("2", "Andrada", 3, 2));
    }

    @Test(expected = ValidationException.class)
    public void saveAssignmentWithEmptyIDTest() {
        service.saveAssignment("", "a", 3, 2);
    }

    @Test(expected = ValidationException.class)
    public void saveAssignmentWithEmptyDescriptionTest() {
        service.saveAssignment("2", "", 3, 2);
    }

    @Test(expected = ValidationException.class)
    public void saveAssignmentWithLessThan1DeadlineTest() {
        service.saveAssignment("2", "a", 0, 2);
    }

    @Test(expected = ValidationException.class)
    public void saveAssignmentWithLessThan1StartlineTest() {
        service.saveAssignment("2", "a", 3, 0);
    }

    @Test
    public void saveAssignmentWithExistingIDTest() {
        assertFalse(service.saveAssignment("1", "Bill", 3, 2));
    }

    // TESTS FOR ADD GRADE

    @Test
    public void saveValidGradeCheckReturnValueTest() {
        assertTrue(service.saveGrade("1", "1", 10, 3, "good job"));
    }

    @Test(expected = ValidationException.class)
    public void saveGradeWithEmptyStudentIDTest() {
        service.saveGrade("", "1", 10, 3, "good job");
    }
}
