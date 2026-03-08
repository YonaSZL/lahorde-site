package yonasazela.lahordeapi.exceptions;

public class DBException extends RuntimeException {
	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message, Object argument, Throwable cause) {
		super(message + (argument != null ? ": " + argument : ""), cause);
	}
}
