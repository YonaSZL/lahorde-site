package yonasazela.lahordeapi.rss.mappers.implementation;

import org.springframework.stereotype.Component;
import yonasazela.lahordeapi.exceptions.NullObjectException;
import yonasazela.lahordeapi.rss.dto.TopicDTO;
import yonasazela.lahordeapi.rss.entities.TopicEntity;
import yonasazela.lahordeapi.rss.mappers.ITopicMapper;

/**
 * Manual implementation of TopicMapper.
 * <p>
 * Responsible for converting TopicEntity objects into TopicDTO objects.
 * Ensures a strict separation between persistence layer and API layer.
 */
@Component
public class TopicMapper implements ITopicMapper {

    private TopicMapper() {
    }

    /**
     * Factory method to create a new TopicMapper instance.
     *
     * @return a new instance of TopicMapper
     */
    public static TopicMapper newTopicMapper() {
        return new TopicMapper();
    }

    /**
     * Converts a TopicEntity into a TopicDTO.
     *
     * @param entity the TopicEntity to convert
     * @return the corresponding TopicDTO
     * @throws NullObjectException if the entity is null
     */
    @Override
    public TopicDTO toDTO(TopicEntity entity) {
        if (entity == null)
            throw NullObjectException.newNullObjectException();

        return new TopicDTO(
                entity.getName()
        );
    }
}