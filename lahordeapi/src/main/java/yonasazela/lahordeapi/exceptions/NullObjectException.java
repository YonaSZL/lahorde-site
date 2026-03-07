package yonasazela.lahordeapi.exceptions;

public class NullObjectException extends IllegalArgumentException {
    public NullObjectException() {
        super("Object cannot be null");
    }
}
