package yonasazela.lahordeapi.rss.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yonasazela.lahordeapi.rss.entities.TopicEntity;

/**
 * Spring Data JPA repository for TopicEntity.
 * <p>
 * Provides basic CRUD operations and custom query methods
 * for accessing topic data from the database.
 */
@Repository
public interface ITopicRepository extends JpaRepository<TopicEntity, Integer> {

    /**
     * Finds a topic by its unique name.
     *
     * @param name the name of the topic
     * @return the matching TopicEntity, or null if not found
     */
    @EntityGraph(attributePaths = "feeds")
    TopicEntity findByName(String name);
}