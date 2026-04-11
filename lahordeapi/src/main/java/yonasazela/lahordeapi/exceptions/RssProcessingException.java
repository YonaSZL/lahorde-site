package yonasazela.lahordeapi.exceptions;

/**
 * Exception thrown when an error occurs while processing RSS articles for a topic.
 */
public class RssProcessingException extends RuntimeException {

    private static final String PREFIX = "Error while building RSS articles for topic: ";

    private RssProcessingException(String topicName) {
        super(PREFIX + topicName);
    }

    /**
     * Factory method to create a new RssProcessingException.
     *
     * @param topicName the topic for which RSS processing failed
     * @return a new instance of RssProcessingException
     */
    public static RssProcessingException newRssProcessingException(String topicName) {
        return new RssProcessingException(topicName);
    }
}