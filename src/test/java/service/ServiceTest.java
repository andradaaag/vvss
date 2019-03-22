package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ServiceTest {
    public Service service;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Service.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();


        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void saveStudentWithExistingIDTest() {
        assertEquals(0, service.saveStudent("1", "A", 933));
    }

    @Test
    public void saveStudentWithEmptyNameTest() {
        assertEquals(0, service.saveStudent("11", "", 933));
    }

    @Test
    public void saveStudentWithNullNameTest() {
        assertEquals(0, service.saveStudent("11", null, 933));
    }

    @Test
    public void saveStudentWithEmptyIdTest() {
        assertEquals(0, service.saveStudent("", "A", 933));
    }

    @Test
    public void saveStudentWithNullIdTest() {
        assertEquals(0, service.saveStudent(null, "A", 933));
    }

    @Test
    public void saveStudentWithWrongGroupNumber1Test() {
        assertEquals(0, service.saveStudent("11", "", 109));
    }

    @Test
    public void saveStudentWithWrongGroupNumber2Test() {
        assertEquals(0, service.saveStudent("11", "", 939));
    }

    @Test
    public void saveStudentWithWrongGroupNumber3Test() {
        assertEquals(0, service.saveStudent("11", "", 0));
    }

    @Test
    public void saveStudentWithWrongGroupNumber4Test() {
        assertEquals(0, service.saveStudent("11", "", -1));
    }

    @Test
    public void saveValidStudent1Test() {
        assertEquals(1, service.saveStudent("11", "Andrada", 933));
        service.deleteStudent("11");
    }

    @Test
    public void saveValidStudent2Test() {
        long numberOfStudents = StreamSupport.stream(service.findAllStudents().spliterator(), false).count();
        service.saveStudent("11", "Andrada", 933);
        assertEquals(numberOfStudents + 1, StreamSupport.stream(service.findAllStudents().spliterator(), false).count());
        service.deleteStudent("11");
    }
}
