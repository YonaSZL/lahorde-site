package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when a string parameter is null or blank.
 */
public class NullStringParameterException extends IllegalArgumentException {
	private NullStringParameterException() {
		super("Object name cannot be null or blank");
	}

    /**
     * Factory method to create a new NullStringParameterException.
     *
     * @return a new instance of NullStringParameterException.
     */
    public static NullStringParameterException newNullStringParameterException() {
        return new NullStringParameterException();
    }
}
