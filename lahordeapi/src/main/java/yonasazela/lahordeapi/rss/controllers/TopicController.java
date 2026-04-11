package yonasazela.lahordeapi.rss.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yonasazela.lahordeapi.rss.dto.ArticleDTO;
import yonasazela.lahordeapi.rss.dto.TopicDTO;
import yonasazela.lahordeapi.rss.services.ITopicService;

import java.util.List;

/**
 * REST controller exposing RSS topics and their articles.
 */
@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

    private final ITopicService service;

    /**
     * Retrieves all topics.
     *
     * @return list of TopicDTO
     */
    @GetMapping
    public List<TopicDTO> getAllTopics() {
        return service.getAllTopics();
    }

    /**
     * Retrieves all RSS articles for a given topic.
     *
     * @param topicName the name of the topic
     * @return list of ArticleDTO
     */
    @GetMapping("/{topicName}/articles")
    public List<ArticleDTO> getArticles(@PathVariable String topicName) {
        return service.getArticlesByTopicName(topicName);
    }
}