package yonasazela.lahordeapi.exceptions;

public class NegativeIdentifierException extends IllegalArgumentException {
    public NegativeIdentifierException() {
        super("Id cannot be negative");
    }
}
