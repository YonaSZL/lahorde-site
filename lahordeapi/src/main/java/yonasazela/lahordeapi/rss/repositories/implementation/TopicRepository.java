package yonasazela.lahordeapi.rss.repositories.implementation;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import yonasazela.lahordeapi.exceptions.DBException;
import yonasazela.lahordeapi.exceptions.NullStringParameterException;
import yonasazela.lahordeapi.exceptions.ObjectNotFoundException;
import yonasazela.lahordeapi.rss.entities.TopicEntity;
import yonasazela.lahordeapi.rss.repositories.ITopicRepository;

import java.util.List;

/**
 * Repository implementation for TopicEntity.
 * <p>
 * Acts as a wrapper around the Spring Data JPA repository to centralize
 * database access and handle exception translation.
 */
@Repository
@Primary
@RequiredArgsConstructor
public class TopicRepository {

    private final ITopicRepository repository;

    private static final String DB_ERROR_RETRIEVE = "retrieving topic";
    private static final String DB_ERROR_RETRIEVE_ALL = "retrieving topics";

    /**
     * Finds a topic by its name.
     *
     * @param name the name of the topic
     * @return the matching TopicEntity
     * @throws NullStringParameterException if the result is null or invalid input is provided
     * @throws DBException if a database error occurs
     */
    public TopicEntity findByName(String name) {
        try {
            TopicEntity entity = repository.findByName(name);

            if (entity == null)
                throw NullStringParameterException.newNullStringParameterException();

            return entity;

        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw DBException.newDBException(DB_ERROR_RETRIEVE, name, e);
        }
    }

    /**
     * Retrieves all topics.
     *
     * @return a list of TopicEntity
     * @throws DBException if a database error occurs
     */
    public @NonNull List<TopicEntity> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw DBException.newDBException(DB_ERROR_RETRIEVE_ALL, e);
        }
    }
}