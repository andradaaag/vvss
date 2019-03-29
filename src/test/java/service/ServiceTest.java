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
    public Service service;

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


    //START TESTS FOR EDGE CASES GROUP
    @Test(expected = ValidationException.class)
    public void saveStudentWithWrongGroupNumber1Test() {
        service.saveStudent("11", "Bill", 109, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithWrongGroupNumber2Test() {
        service.saveStudent("11", "Bill", 939, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithWrongGroupNumber3Test() {
        assertFalse(service.saveStudent("11", "Bill", 0, "email", "professor"));
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithWrongGroupNumber4Test() {
        service.saveStudent("11", "Bill", -1, "email", "professor");
    }

    public void saveStudentWithProperGroupNumber1Test() {
        assertFalse(service.saveStudent("11", "Bill", 110, "email", "professor"));
    }

    public void saveStudentWithProperGroupNumber2Test() {
        service.saveStudent("11", "Bill", 111, "email", "professor");
    }

    public void saveStudentWithProperGroupNumber3Test() {
        assertFalse(service.saveStudent("11", "Bill", 938, "email", "professor"));
    }

    public void saveStudentWithProperGroupNumber4Test() {
        service.saveStudent("11", "Bill", 937, "email", "professor");
    }

    //END TESTS FOR EDGE CASES GROUP

    @Test
    public void saveValidStudent1Test() {
        assertTrue(service.saveStudent("11", "Andrada", 933, "email", "professor"));
    }

    @Test
    public void saveValidStudent2Test() {
        long numberOfStudents = StreamSupport.stream(service.getAllStudents().spliterator(), false).count();
        service.saveStudent("11", "Andrada", 933, "email", "professor");
        assertEquals(numberOfStudents + 1, StreamSupport.stream(service.getAllStudents().spliterator(), false).count());
        service.deleteStudent("11");
    }
}
