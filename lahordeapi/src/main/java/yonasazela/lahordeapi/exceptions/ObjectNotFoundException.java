package yonasazela.lahordeapi.exceptions;

public class ObjectNotFoundException extends RuntimeException {
	public ObjectNotFoundException(int id) {
		super("Object not found with id : " + id);
	}
}
