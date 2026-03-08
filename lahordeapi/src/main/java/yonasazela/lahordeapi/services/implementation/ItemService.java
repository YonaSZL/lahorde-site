package yonasazela.lahordeapi.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.DBException;
import yonasazela.lahordeapi.exceptions.NegativeIdentifierException;
import yonasazela.lahordeapi.exceptions.NullObjectException;
import yonasazela.lahordeapi.exceptions.NullStringParameterException;
import yonasazela.lahordeapi.exceptions.ObjectAlreadyExistsException;
import yonasazela.lahordeapi.exceptions.ObjectNotFoundException;
import yonasazela.lahordeapi.exceptions.ZeroUpdateIdentifierException;
import yonasazela.lahordeapi.mappers.implementation.ItemMapper;
import yonasazela.lahordeapi.repositories.ItemRepository;
import yonasazela.lahordeapi.services.IItemService;

import java.util.List;

/**
 * Implementation of IItemService.
 * Handles business logic and coordinates data access for items.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService implements IItemService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	private static final String DB_ERROR_SAVE = "saving object";
	private static final String DB_ERROR_RETRIEVE = "retrieving object";
	private static final String DB_ERROR_CHECK_EXISTENCE = "checking existence";
	private static final String DB_ERROR_DELETE = "deleting object";
	private static final String DB_ERROR_RETRIEVE_ALL = "retrieving all objects";

	// ===== SECTION 1 - CRUD DATABASE ONE ENTITY =====

	// CREATE / UPDATE
	private ItemEntity save(ItemEntity entity) {
		try {
			return itemRepository.save(entity);
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_SAVE, e);
		}
	}

	// READ
	private ItemEntity findById(int id) {
		try {
			return itemRepository.findById(id).orElseThrow(() -> ObjectNotFoundException.newObjectNotFoundException(id));
		} catch (ObjectNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_RETRIEVE, id, e);
		}
	}

	// EXISTENCE CHECKS
	private boolean existsById(int id) {
		try {
			return itemRepository.existsById(id);
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_CHECK_EXISTENCE, id, e);
		}
	}

	private boolean existsByName(String name) {
		try {
			return itemRepository.existsByName(name);
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_CHECK_EXISTENCE, name, e);
		}
	}

	// DELETE
	private void deleteById(int id) {
		try {
			itemRepository.deleteById(id);
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_DELETE, id, e);
		}
	}

	// ===== SECTION 2 - CRUD DATABASE MULTIPLE ENTITIES =====

	private List<ItemEntity> findAll() {
		try {
			return itemRepository.findAll();
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_RETRIEVE_ALL, e);
		}
	}

	// ===== SECTION 3 - CRUD API ONE ENTITY =====

	@Override
	public ItemDTO getItemById(int id) {
		if (id < 0)
			throw NegativeIdentifierException.newNegativeIdentifierException();

		ItemEntity entity = findById(id);
		return itemMapper.toDTO(entity);
	}

	@Override
	public ItemDTO createItem(ItemDTO dto) {
		if (dto == null)
			throw NullObjectException.newNullObjectException();
		if (dto.getName() == null || dto.getName().isBlank())
			throw NullStringParameterException.newNullStringParameterException();

		if (existsByName(dto.getName()))
			throw ObjectAlreadyExistsException.newObjectAlreadyExistsException("name", dto.getName());

		ItemEntity entity = itemMapper.toEntity(dto);
		entity.setId(0);

		ItemEntity saved = save(entity);
		return itemMapper.toDTO(saved);
	}

	@Override
	public ItemDTO updateItem(int id, ItemDTO dto) {
		if (id < 0)
			throw NegativeIdentifierException.newNegativeIdentifierException();
		if (id == 0)
			throw ZeroUpdateIdentifierException.newZeroUpdateIdentifierException();
		if (dto == null)
			throw NullObjectException.newNullObjectException();

		ItemEntity entity = findById(id);
		itemMapper.updateEntityFromDTO(dto, entity);

		ItemEntity updated = save(entity);
		return itemMapper.toDTO(updated);
	}

	@Override
	public void deleteItem(int id) {
		if (id < 0)
			throw NegativeIdentifierException.newNegativeIdentifierException();
		if (!existsById(id))
			throw ObjectNotFoundException.newObjectNotFoundException(id);

		deleteById(id);
	}

	// ===== SECTION 4 - CRUD API MULTIPLE ENTITIES =====

	@Override
	public List<ItemDTO> getAllItems() {
		List<ItemEntity> entities = findAll();
		return entities.stream().map(itemMapper::toDTO).toList();
	}
}
