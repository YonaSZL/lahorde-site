package yonasazela.lahordeapi.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.*;
import yonasazela.lahordeapi.mappers.implementation.ItemMapper;
import yonasazela.lahordeapi.repositories.ItemRepository;
import yonasazela.lahordeapi.services.IItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService implements IItemService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	// ===== SECTION 1 - CRUD DATABASE ONE ENTITY =====

	// CREATE / UPDATE
	private ItemEntity save(ItemEntity entity) {
		try {
			return itemRepository.save(entity);
		} catch (Exception e) {
			throw new DBException("Error while saving object", e);
		}
	}

	// READ
	private ItemEntity findById(int id) {
		try {
			return itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
		} catch (ObjectNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new DBException("Error while retrieving object", id, e);
		}
	}

	// EXISTENCE CHECKS
	private boolean existsById(int id) {
		try {
			return itemRepository.existsById(id);
		} catch (Exception e) {
			throw new DBException("Error while checking existence", id, e);
		}
	}

	private boolean existsByName(String name) {
		try {
			return itemRepository.existsByName(name);
		} catch (Exception e) {
			throw new DBException("Error while checking existence", name, e);
		}
	}

	// DELETE
	private void deleteById(int id) {
		try {
			itemRepository.deleteById(id);
		} catch (Exception e) {
			throw new DBException("Error while deleting object", id, e);
		}
	}

	// ===== SECTION 2 - CRUD DATABASE MULTIPLE ENTITIES =====

	private List<ItemEntity> findAll() {
		try {
			return itemRepository.findAll();
		} catch (Exception e) {
			throw new DBException("Error while retrieving all objects", e);
		}
	}

	// ===== SECTION 3 - CRUD API ONE ENTITY =====

	@Override
	public ItemDTO getItemById(int id) {
		if (id < 0)
			throw new NegativeIdentifierException();

		ItemEntity entity = findById(id);
		return itemMapper.toDTO(entity);
	}

	@Override
	public ItemDTO createItem(ItemDTO dto) {
		if (dto == null)
			throw new NullObjectException();
		if (dto.getName() == null || dto.getName().isBlank())
			throw new NullStringParameterException();

		if (existsByName(dto.getName()))
			throw new ObjectAlreadyExistsException("name", dto.getName());

		ItemEntity entity = itemMapper.toEntity(dto);
		entity.setId(0);

		ItemEntity saved = save(entity);
		return itemMapper.toDTO(saved);
	}

	@Override
	public ItemDTO updateItem(int id, ItemDTO dto) {
		if (id < 0)
			throw new NegativeIdentifierException();
		if (id == 0)
			throw new ZeroUpdateIdentifierException();
		if (dto == null)
			throw new NullObjectException();

		ItemEntity entity = findById(id);
		itemMapper.updateEntityFromDTO(dto, entity);

		ItemEntity updated = save(entity);
		return itemMapper.toDTO(updated);
	}

	@Override
	public void deleteItem(int id) {
		if (id < 0)
			throw new NegativeIdentifierException();
		if (!existsById(id))
			throw new ObjectNotFoundException(id);

		deleteById(id);
	}

	// ===== SECTION 4 - CRUD API MULTIPLE ENTITIES =====

	@Override
	public List<ItemDTO> getAllItems() {
		List<ItemEntity> entities = findAll();
		return entities.stream().map(itemMapper::toDTO).toList();
	}
}
