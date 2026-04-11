package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when a zero identifier is provided for an update operation.
 */
public class ZeroUpdateIdentifierException extends IllegalArgumentException {
	private ZeroUpdateIdentifierException() {
		super("Id cannot be 0 for update");
	}

	/**
	 * Factory method to create a new ZeroUpdateIdentifierException.
	 *
	 * @return a new instance of ZeroUpdateIdentifierException.
	 */
	public static ZeroUpdateIdentifierException newZeroUpdateIdentifierException() {
		return new ZeroUpdateIdentifierException();
	}
}
