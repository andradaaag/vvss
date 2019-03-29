package repository;

import domain.Grade;
import exception.ValidationException;
import javafx.util.Pair;
import validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GradeRepo {
    protected StudentRepo studentRepo;
    protected AssignmentRepo assignmentRepo;
    private HashMap<Pair<String, String>, Grade> data;
    private Validator<Grade> validator;

    public GradeRepo(Validator<Grade> validator, StudentRepo studentRepo, AssignmentRepo assignmentRepo) {
        this.studentRepo = studentRepo;
        this.assignmentRepo = assignmentRepo;
        this.validator = validator;
        data = new HashMap<>();
    }

    public Grade find(Pair<String, String> id) {
        return data.get(id);
    }

    public List<Grade> findAll() {
        return new ArrayList<>(data.values());
    }

    public boolean addGrade(Grade entity) {
        validator.validate(entity);
        if (studentRepo.find(entity.getGradeId().getKey()) == null || assignmentRepo.find(entity.getGradeId().getValue()) == null)
            throw new ValidationException("invalid student or assignment id");
        return data.putIfAbsent(entity.getGradeId(), entity) == null;
    }
}
