package yonasazela.lahordeapi.rss.services.implementation;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yonasazela.lahordeapi.exceptions.RssFetchException;
import yonasazela.lahordeapi.exceptions.RssProcessingException;
import yonasazela.lahordeapi.rss.dto.ArticleDTO;
import yonasazela.lahordeapi.rss.dto.TopicDTO;
import yonasazela.lahordeapi.rss.entities.TopicEntity;
import yonasazela.lahordeapi.rss.mappers.implementation.TopicMapper;
import yonasazela.lahordeapi.rss.repositories.implementation.TopicRepository;
import yonasazela.lahordeapi.rss.services.ITopicService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Service responsible for aggregating RSS feeds for a given topic.
 * <p>
 * It retrieves all feeds linked to a topic, fetches their RSS content,
 * filters articles published within the last 7 days, and converts them
 * into DTOs for API exposure.
 */
@Service
@RequiredArgsConstructor
public class TopicService implements ITopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    /**
     * Retrieves all available topics.
     *
     * @return a list of TopicDTO representing all topics
     */
    @Override
    public List<TopicDTO> getAllTopics() {

        List<TopicEntity> entities = topicRepository.findAll();

        return entities.stream()
                .map(topicMapper::toDTO)
                .toList();
    }

    /**
     * Retrieves all RSS articles for a given topic name.
     * <p>
     * Articles are aggregated from all feeds linked to the topic
     * and filtered to include only those published in the last 7 days.
     *
     * @param topicName the name of the topic
     * @return a list of ArticleDTO sorted by publication date (descending)
     * @throws RssProcessingException if any error occurs during processing
     */
    @Override
    public List<ArticleDTO> getArticlesByTopicName(String topicName) {

        try {
            TopicEntity topic = topicRepository.findByName(topicName);

            Instant limit = Instant.now().minus(Duration.ofDays(7));

            List<ArticleDTO> result = new ArrayList<>();

            for (var feed : topic.getFeeds()) {

                SyndFeed rss = fetchRssFeed(feed.getFeed());

                for (SyndEntry entry : rss.getEntries()) {

                    Instant published = toInstant(entry.getPublishedDate(), entry.getUpdatedDate());
                    if (published == null || published.isBefore(limit)) continue;

                    String id = UUID.nameUUIDFromBytes(entry.getLink().getBytes()).toString();

                    result.add(new ArticleDTO(
                            id,
                            entry.getTitle(),
                            entry.getLink(),
                            entry.getDescription() != null ? entry.getDescription().getValue() : null,
                            published
                    ));
                }
            }

            result.sort(Comparator.comparing(ArticleDTO::publishedAt).reversed());

            return result;

        } catch (Exception _) {
            throw RssProcessingException.newRssProcessingException(topicName);
        }
    }

    /**
     * Fetches and parses an RSS feed from a given URL.
     *
     * @param feedUrl the RSS feed URL
     * @return a parsed SyndFeed object
     * @throws RssFetchException if the feed cannot be fetched or parsed
     */
    private SyndFeed fetchRssFeed(String feedUrl) {

        try {
            return new SyndFeedInput().build(new XmlReader(openStream(feedUrl)));
        } catch (Exception _) {
            throw RssFetchException.newRssFetchException();
        }
    }

    /**
     * Opens a stream to a given URL with timeout configuration.
     *
     * @param url the URL to open
     * @return an InputStream from the connection
     * @throws IOException if the connection fails
     */
    private InputStream openStream(String url) throws IOException {
        URLConnection connection = URI.create(url).toURL().openConnection();

        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);

        return connection.getInputStream();
    }

    /**
     * Converts published/updated dates into an Instant.
     *
     * @param published published date
     * @param updated updated date
     * @return Instant or null if both are null
     */
    private Instant toInstant(Date published, Date updated) {
        Date d = (published != null) ? published : updated;
        return (d != null) ? d.toInstant() : null;
    }
}