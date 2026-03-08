package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when a null object is encountered where one is expected.
 */
public class NullObjectException extends IllegalArgumentException {
	private NullObjectException() {
		super("Object cannot be null");
	}

    /**
     * Factory method to create a new NullObjectException.
     *
     * @return a new instance of NullObjectException.
     */
    public static NullObjectException newNullObjectException() {
        return new NullObjectException();
    }
}
