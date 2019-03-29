package repository;

import validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDRepo<T, ID> {
    private Map<ID, T> data;
    private Validator<T> validator;

    public CRUDRepo(Validator<T> validator) {
        data = new HashMap<>();
        this.validator = validator;
    }

    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public T find(ID id) {
        return data.get(id);
    }

    /**
     * on success return true
     */
    public boolean delete(ID id) {
        return data.remove(id) != null;
    }

    /**
     * on success return true
     */
    public boolean save(ID id, T entity) {
        validator.validate(entity);
        return data.putIfAbsent(id, entity) == null;
    }

    /**
     * on success return true
     */
    public boolean update(ID id, T entity) {
        validator.validate(entity);
        return data.replace(id, entity) != null;
    }
}
