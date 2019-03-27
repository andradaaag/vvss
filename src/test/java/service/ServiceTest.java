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
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentRepo studentRepo = new StudentRepo(studentValidator);
        AssignmentRepo assignmentRepo = new AssignmentRepo(assignmentValidator);
        GradeRepo gradeRepo = new GradeRepo(gradeValidator, studentRepo, assignmentRepo);

        service = new Service(studentRepo, assignmentRepo, gradeRepo);
        service.saveStudent("1", "Bob", 932, "xXxBobBobitzaxXx@gmail.com", "prof");
    }

    @Test
    public void saveStudentWithExistingIDTest() {
        assertFalse(service.saveStudent("1", "A", 933, "email", "professor"));
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
        service.saveStudent("", "A", 933, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithNullIdTest() {
        service.saveStudent(null, "A", 933, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithWrongGroupNumber1Test() {
        service.saveStudent("11", "", 109, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithWrongGroupNumber2Test() {
        service.saveStudent("11", "", 939, "email", "professor");
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithWrongGroupNumber3Test() {
        assertFalse(service.saveStudent("11", "", 0, "email", "professor"));
    }

    @Test(expected = ValidationException.class)
    public void saveStudentWithWrongGroupNumber4Test() {
        service.saveStudent("11", "", -1, "email", "professor");
    }

    @Test
    public void saveValidStudent1Test() {
        assertTrue(service.saveStudent("11", "Andrada", 933, "email", "professor"));
        service.deleteStudent("11");
    }

    @Test
    public void saveValidStudent2Test() {
        long numberOfStudents = StreamSupport.stream(service.getAllStudents().spliterator(), false).count();
        service.saveStudent("11", "Andrada", 933, "email", "professor");
        assertEquals(numberOfStudents + 1, StreamSupport.stream(service.getAllStudents().spliterator(), false).count());
        service.deleteStudent("11");
    }
}
