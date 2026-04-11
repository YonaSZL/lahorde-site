package yonasazela.lahordeapi.kenshi.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yonasazela.lahordeapi.kenshi.dto.ItemDTO;
import yonasazela.lahordeapi.kenshi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.NegativeIdentifierException;
import yonasazela.lahordeapi.exceptions.NullObjectException;
import yonasazela.lahordeapi.exceptions.NullStringParameterException;
import yonasazela.lahordeapi.exceptions.ObjectAlreadyExistsException;
import yonasazela.lahordeapi.exceptions.ObjectNotFoundException;
import yonasazela.lahordeapi.exceptions.ZeroUpdateIdentifierException;
import yonasazela.lahordeapi.kenshi.mappers.implementation.ItemMapper;
import yonasazela.lahordeapi.kenshi.repositories.implementation.ItemRepository;
import yonasazela.lahordeapi.kenshi.services.IItemService;

import java.util.List;

/**
 * Implementation of IItemService. Handles business logic and coordinates data
 * access for items.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService implements IItemService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	// ===== SECTION 1 - CRUD API ONE ENTITY =====

	/**
	 * Retrieves an item by its ID.
	 *
	 * @param id
	 *            the ID of the item to retrieve.
	 * @return the found ItemDTO.
	 * @throws NegativeIdentifierException
	 *             if the given ID is negative.
	 */
	@Override
	public ItemDTO getItemById(int id) {
		if (id < 0)
			throw NegativeIdentifierException.newNegativeIdentifierException();

		ItemEntity entity = itemRepository.findById(id);
		return itemMapper.toDTO(entity);
	}

	/**
	 * Creates a new item.
	 *
	 * @param dto
	 *            the ItemDTO containing the details of the item to create.
	 * @return the created ItemDTO.
	 * @throws NullObjectException
	 *             if the given ItemDTO is null.
	 * @throws NullStringParameterException
	 *             if the item's name is null or blank.
	 * @throws ObjectAlreadyExistsException
	 *             if an item with the same name already exists.
	 */
	@Override
	public ItemDTO createItem(ItemDTO dto) {
		if (dto == null)
			throw NullObjectException.newNullObjectException();
		if (dto.getName() == null || dto.getName().isBlank())
			throw NullStringParameterException.newNullStringParameterException();

		if (itemRepository.existsByName(dto.getName()))
			throw ObjectAlreadyExistsException.newObjectAlreadyExistsException("name", dto.getName());

		ItemEntity entity = itemMapper.toEntity(dto);
		entity.setId(0);

		ItemEntity saved = itemRepository.save(entity);
		return itemMapper.toDTO(saved);
	}

	/**
	 * Updates an existing item.
	 *
	 * @param id
	 *            the ID of the item to update.
	 * @param dto
	 *            the ItemDTO containing the new details.
	 * @return the updated ItemDTO.
	 * @throws NegativeIdentifierException
	 *             if the given ID is negative.
	 * @throws ZeroUpdateIdentifierException
	 *             if the given ID is zero.
	 * @throws NullObjectException
	 *             if the given ItemDTO is null.
	 */
	@Override
	public ItemDTO updateItem(int id, ItemDTO dto) {
		if (id < 0)
			throw NegativeIdentifierException.newNegativeIdentifierException();
		if (id == 0)
			throw ZeroUpdateIdentifierException.newZeroUpdateIdentifierException();
		if (dto == null)
			throw NullObjectException.newNullObjectException();

		ItemEntity entity = itemRepository.findById(id);
		itemMapper.updateEntityFromDTO(dto, entity);

		ItemEntity updated = itemRepository.save(entity);
		return itemMapper.toDTO(updated);
	}

	/**
	 * Deletes an item by its ID.
	 *
	 * @param id
	 *            the ID of the item to delete.
	 * @throws NegativeIdentifierException
	 *             if the given ID is negative.
	 * @throws ObjectNotFoundException
	 *             if no item is found with the given ID.
	 */
	@Override
	public void deleteItem(int id) {
		if (id < 0)
			throw NegativeIdentifierException.newNegativeIdentifierException();
		if (!itemRepository.existsById(id))
			throw ObjectNotFoundException.newObjectNotFoundException(id);

		itemRepository.deleteById(id);
	}

	// ===== SECTION 2 - CRUD API MULTIPLE ENTITIES =====

	/**
	 * Retrieves all items.
	 *
	 * @return a list of ItemDTO objects.
	 */
	@Override
	public List<ItemDTO> getAllItems() {
		List<ItemEntity> entities = itemRepository.findAll();
		return entities.stream().map(itemMapper::toDTO).toList();
	}
}
