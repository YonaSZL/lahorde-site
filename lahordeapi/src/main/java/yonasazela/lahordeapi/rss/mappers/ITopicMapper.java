package yonasazela.lahordeapi.rss.mappers;

import yonasazela.lahordeapi.rss.dto.TopicDTO;
import yonasazela.lahordeapi.rss.entities.TopicEntity;

/**
 * Mapper responsible for converting TopicEntity into TopicDTO.
 * <p>
 * This abstraction isolates the service layer from persistence models
 * and ensures a clean separation between database entities and API contracts.
 */
public interface ITopicMapper {

    /**
     * Converts a TopicEntity into a TopicDTO.
     *
     * @param entity the TopicEntity to convert
     * @return the corresponding TopicDTO
     */
    TopicDTO toDTO(TopicEntity entity);
}