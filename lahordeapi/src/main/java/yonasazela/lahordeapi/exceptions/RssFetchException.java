package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when an RSS feed cannot be fetched or parsed.
 */
public class RssFetchException extends RuntimeException {

    /**
     * Constructs a new RssFetchException with a fixed error message.
     */
    private RssFetchException() {
        super("Error while fetching RSS feed");
    }

    /**
     * Factory method to create a new RssFetchException.
     *
     * @return a new instance of RssFetchException
     */
    public static RssFetchException newRssFetchException() {
        return new RssFetchException();
    }
}