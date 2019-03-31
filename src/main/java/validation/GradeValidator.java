package validation;
import domain.Grade;
import exception.ValidationException;

public class GradeValidator implements Validator<Grade> {
    public void validate(Grade grade) throws ValidationException {
        if (grade.getGradeId().getKey() == null || grade.getGradeId().getKey().equals("")) {
            throw new ValidationException("Invalid ID Student! \n");
        }
        if (grade.getGradeId().getValue() == null || grade.getGradeId().getValue().equals("")) {
            throw new ValidationException("Invalid ID Assignment! \n");
        }
        if (grade.getGrade() < 0 || grade.getGrade() > 10) {
            throw new ValidationException("Invalid grade value! \n");
        }
        if (grade.getDelivery() < 0) {
            throw new ValidationException("Invalid delivery week! \n");
        }
    }
}
