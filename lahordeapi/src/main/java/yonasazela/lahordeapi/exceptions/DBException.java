package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when a database error occurs.
 */
public class DBException extends RuntimeException {

    private static final String PREFIX = "Error while ";

    private DBException(String action, Throwable cause) {
        super(PREFIX + action, cause);
    }

    private DBException(String action, Object argument, Throwable cause) {
        super(PREFIX + action + (argument != null ? ": " + argument : ""), cause);
    }

    /**
     * Factory method to create a new DBException with an action and a cause.
     *
     * @param action the action being performed when the error occurred.
     * @param cause the cause of the exception.
     * @return a new instance of DBException.
     */
    public static DBException newDBException(String action, Throwable cause) {
        return new DBException(action, cause);
    }

    /**
     * Factory method to create a new DBException with an action, an argument, and a cause.
     *
     * @param action the action being performed when the error occurred.
     * @param argument the argument associated with the action.
     * @param cause the cause of the exception.
     * @return a new instance of DBException.
     */
    public static DBException newDBException(String action, Object argument, Throwable cause) {
        return new DBException(action, argument, cause);
    }
}
