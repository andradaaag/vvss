package repository;

import domain.Student;
import validation.Validator;

public class StudentRepo extends CRUDRepo<Student, String> {

    public StudentRepo(Validator<Student> validator) {
        super(validator);
    }

    public boolean save(Student entity) {
        return super.save(entity.getID(), entity);
    }

    public boolean update(Student entity) {
        return super.update(entity.getID(), entity);
    }
}
