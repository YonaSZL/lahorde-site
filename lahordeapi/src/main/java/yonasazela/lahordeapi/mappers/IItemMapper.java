package yonasazela.lahordeapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;

/**
 * Mapper interface for converting between ItemEntity and ItemDTO.
 * Uses MapStruct for automatic implementation.
 */
@Mapper(componentModel = "spring")
public interface IItemMapper {

	/**
	 * Converts an ItemEntity to an ItemDTO.
	 *
	 * @param entity the ItemEntity to convert.
	 * @return the converted ItemDTO.
	 */
	ItemDTO toDTO(ItemEntity entity);

	/**
	 * Converts an ItemDTO to an ItemEntity.
	 *
	 * @param dto the ItemDTO to convert.
	 * @return the converted ItemEntity.
	 */
	ItemEntity toEntity(ItemDTO dto);

	/**
	 * Updates an existing ItemEntity from an ItemDTO.
	 *
	 * @param dto    the ItemDTO containing the new data.
	 * @param entity the ItemEntity to update.
	 */
	void updateEntityFromDTO(ItemDTO dto, @MappingTarget ItemEntity entity);
}
