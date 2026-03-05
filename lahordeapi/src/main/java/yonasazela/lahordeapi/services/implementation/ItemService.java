package yonasazela.lahordeapi.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;
import yonasazela.lahordeapi.mappers.implementation.ItemMapper;
import yonasazela.lahordeapi.repositories.ItemRepository;
import yonasazela.lahordeapi.services.IItemService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService implements IItemService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper; // Injection de notre mapper avec builder

	@Override
	public List<ItemDTO> getAllItems() {
		return itemRepository.findAll().stream().map(itemMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public ItemDTO getItemById(int id) {
		ItemEntity entity = itemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
		return itemMapper.toDTO(entity);
	}

	@Override
	public ItemDTO createItem(ItemDTO dto) {
		// Vérifier qu'aucun item avec le même nom n’existe
		Optional<ItemEntity> existing = itemRepository.findByName(dto.getName());
		if (existing.isPresent()) {
			throw new RuntimeException("Item with name already exists: " + dto.getName());
		}

		// Convertir DTO en Entity avec builder
		ItemEntity entity = itemMapper.toEntity(dto);
		entity.setId(0); // s'assurer que c'est un insert

		ItemEntity saved = itemRepository.save(entity);
		return itemMapper.toDTO(saved);
	}

	@Override
	public ItemDTO updateItem(int id, ItemDTO dto) {
		// Récupérer l'entity existante
		ItemEntity entity = itemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cannot update non-existent item with id: " + id));

		// Mapper uniquement les champs modifiables depuis le DTO
		itemMapper.updateEntityFromDTO(dto, entity);

		ItemEntity updated = itemRepository.save(entity);
		return itemMapper.toDTO(updated);
	}

	@Override
	public void deleteItem(int id) {
		if (!itemRepository.existsById(id)) {
			throw new RuntimeException("Item not found with id: " + id);
		}
		itemRepository.deleteById(id);
	}
}
