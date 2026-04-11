package yonasazela.lahordeapi.kenshi.mappers.implementation;

import org.springframework.stereotype.Component;
import yonasazela.lahordeapi.kenshi.dto.ItemDTO;
import yonasazela.lahordeapi.kenshi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.NullObjectException;
import yonasazela.lahordeapi.kenshi.mappers.IItemMapper;

/**
 * Manual implementation of IItemMapper. Provides logic to convert between
 * ItemEntity and ItemDTO.
 */
@Component
public class ItemMapper implements IItemMapper {

	private ItemMapper() {
	}

	/**
	 * Factory method to create a new instance of ItemMapper.
	 *
	 * @return a new ItemMapper instance.
	 */
	public static ItemMapper newItemMapper() {
		return new ItemMapper();
	}

	/**
	 * Converts an ItemEntity to an ItemDTO. Throws NullObjectException if the
	 * entity is null.
	 *
	 * @param entity
	 *            the ItemEntity to convert.
	 * @return the converted ItemDTO.
	 */
	@Override
	public ItemDTO toDTO(ItemEntity entity) {
		if (entity == null)
			throw NullObjectException.newNullObjectException();

		return ItemDTO.builder().id(entity.getId()).name(entity.getName()).price(entity.getPrice())
				.weight(entity.getWeight()).size(entity.getSize()).priceForOneSlot(entity.getPriceForOneSlot()) // lecture
				// seule
				.description(entity.getDescription()).build();
	}

	/**
	 * Converts an ItemDTO to an ItemEntity. Throws NullObjectException if the DTO
	 * is null.
	 *
	 * @param dto
	 *            the ItemDTO to convert.
	 * @return the converted ItemEntity.
	 */
	@Override
	public ItemEntity toEntity(ItemDTO dto) {
		if (dto == null)
			throw NullObjectException.newNullObjectException();

		return ItemEntity.builder().id(dto.getId()).name(dto.getName()).price(dto.getPrice()).weight(dto.getWeight())
				.size(dto.getSize())
				// NE PAS SETTER priceForOneSlot
				.description(dto.getDescription()).build();
	}

	/**
	 * Updates an existing ItemEntity from an ItemDTO. Throws NullObjectException if
	 * either the DTO or the entity is null.
	 *
	 * @param dto
	 *            the ItemDTO containing the new data.
	 * @param entity
	 *            the ItemEntity to update.
	 */
	@Override
	public void updateEntityFromDTO(ItemDTO dto, ItemEntity entity) {
		if (dto == null || entity == null)
			throw NullObjectException.newNullObjectException();

		// Mettre à jour uniquement les champs modifiables
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setWeight(dto.getWeight());
		entity.setSize(dto.getSize());
		// NE PAS TOUCHER PriceForOneSlot
		entity.setDescription(dto.getDescription());
	}
}
