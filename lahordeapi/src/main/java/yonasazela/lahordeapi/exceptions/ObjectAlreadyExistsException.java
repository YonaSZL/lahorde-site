package yonasazela.lahordeapi.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String field, Object value) {
        super("Object with " + field + " already exists: " + value);
    }
}
