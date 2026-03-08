package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when an object is not found in the database.
 */
public class ObjectNotFoundException extends RuntimeException {
	private ObjectNotFoundException(int id) {
		super("Object not found with id : " + id);
	}

    /**
     * Factory method to create a new ObjectNotFoundException.
     *
     * @param id the ID of the object that was not found.
     * @return a new instance of ObjectNotFoundException.
     */
    public static ObjectNotFoundException newObjectNotFoundException(int id) {
        return new ObjectNotFoundException(id);
    }
}
