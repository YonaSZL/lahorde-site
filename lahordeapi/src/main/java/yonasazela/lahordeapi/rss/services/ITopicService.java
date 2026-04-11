package yonasazela.lahordeapi.rss.services;

import yonasazela.lahordeapi.rss.dto.ArticleDTO;
import yonasazela.lahordeapi.rss.dto.TopicDTO;

import java.util.List;

/**
 * Service interface for managing topics and aggregating RSS articles.
 * <p>
 * Defines the contract for retrieving topics and fetching RSS articles
 * associated with a given topic name.
 */
public interface ITopicService {

    /**
     * Retrieves all available topics.
     *
     * @return a list of TopicDTO
     */
    List<TopicDTO> getAllTopics();

    /**
     * Retrieves RSS articles for a given topic name.
     *
     * @param topicName the name of the topic
     * @return a list of ArticleDTO filtered and aggregated from RSS feeds
     */
    List<ArticleDTO> getArticlesByTopicName(String topicName);
}