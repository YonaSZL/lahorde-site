package yonasazela.lahordeapi.exceptions;

public class ZeroUpdateIdentifierException extends IllegalArgumentException {
    public ZeroUpdateIdentifierException() {
        super("Id cannot be 0 for update");
    }
}
