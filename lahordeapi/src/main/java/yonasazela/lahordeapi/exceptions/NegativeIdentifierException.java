package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when a negative identifier is provided.
 */
public class NegativeIdentifierException extends IllegalArgumentException {
	private NegativeIdentifierException() {
		super("Id cannot be negative");
	}

    /**
     * Factory method to create a new NegativeIdentifierException.
     *
     * @return a new instance of NegativeIdentifierException.
     */
    public static NegativeIdentifierException newNegativeIdentifierException() {
        return new NegativeIdentifierException();
    }
}
