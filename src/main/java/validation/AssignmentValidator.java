package validation;
import domain.Assignment;
import exception.ValidationException;

public class AssignmentValidator implements Validator<Assignment> {
    public void validate(Assignment assignment) throws ValidationException {
        if (assignment.getID() == null || assignment.getID().equals("")) {
            throw new ValidationException("ID invalid! \n");
        }
        if (assignment.getDescription() == null || assignment.getDescription().equals("")) {
            throw new ValidationException("Descriere invalida! \n");
        }
        if (assignment.getDeadline() < 1 || assignment.getDeadline() > 14 || assignment.getDeadline() < assignment.getStartline()) {
            throw new ValidationException("Deadline invalid! \n");
        }
        if (assignment.getStartline() < 1 || assignment.getStartline() > 14 || assignment.getStartline() > assignment.getDeadline()) {
            throw new ValidationException("Data de primire invalida! \n");
        }
    }
}

