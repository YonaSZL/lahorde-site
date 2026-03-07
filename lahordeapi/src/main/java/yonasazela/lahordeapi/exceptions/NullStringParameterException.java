package yonasazela.lahordeapi.exceptions;

public class NullStringParameterException extends IllegalArgumentException {
    public NullStringParameterException() {
        super("Object name cannot be null or blank");
    }
}
