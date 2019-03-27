package repository;

import domain.Assignment;
import validation.Validator;

public class AssignmentRepo extends CRUDRepo<Assignment, String> {

    public AssignmentRepo(Validator<Assignment> validator) {
        super(validator);
    }

    public boolean save(Assignment entity) {
        return super.save(entity.getID(), entity);
    }

    public boolean update(Assignment entity) {
        return super.update(entity.getID(), entity);
    }
}
