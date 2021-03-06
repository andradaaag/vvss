package validation;

import exception.ValidationException;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}