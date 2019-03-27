package validation;
import domain.Student;
import exception.ValidationException;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        if (student.getID() == null || student.getID().equals("")) {
            throw new ValidationException("Invalid id! \n");
        }
        if (student.getName() == null || student.getName().equals("")) {
            throw new ValidationException("Invalid Name! \n");
        }
        if (student.getGroup() <= 110 || student.getGroup() >= 938) {
            throw new ValidationException("Invalid group! \n");
        }
    }
}

