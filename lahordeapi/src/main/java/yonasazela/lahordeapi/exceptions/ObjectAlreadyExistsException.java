package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when an object already exists in the database.
 */
public class ObjectAlreadyExistsException extends RuntimeException {
	private ObjectAlreadyExistsException(String field, Object value) {
		super("Object with " + field + " already exists: " + value);
	}

	/**
	 * Factory method to create a new ObjectAlreadyExistsException.
	 *
	 * @param field
	 *            the field that caused the conflict.
	 * @param value
	 *            the value that already exists.
	 * @return a new instance of ObjectAlreadyExistsException.
	 */
	public static ObjectAlreadyExistsException newObjectAlreadyExistsException(String field, Object value) {
		return new ObjectAlreadyExistsException(field, value);
	}
}
