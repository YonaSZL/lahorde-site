package yonasazela.lahordeapi.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yonasazela.lahordeapi.kenshi.dto.ItemDTO;
import yonasazela.lahordeapi.kenshi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.NullObjectException;
import yonasazela.lahordeapi.exceptions.NegativeIdentifierException;
import yonasazela.lahordeapi.exceptions.ObjectNotFoundException;
import yonasazela.lahordeapi.exceptions.ObjectAlreadyExistsException;
import yonasazela.lahordeapi.exceptions.ZeroUpdateIdentifierException;
import yonasazela.lahordeapi.exceptions.NullStringParameterException;
import yonasazela.lahordeapi.kenshi.mappers.implementation.ItemMapper;
import yonasazela.lahordeapi.kenshi.repositories.implementation.ItemRepository;
import yonasazela.lahordeapi.kenshi.services.implementation.ItemService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private ItemMapper itemMapper;

	@InjectMocks
	private ItemService itemService;

	private ItemEntity entity;
	private ItemEntity savedEntity;
	private ItemEntity otherEntity;
	private ItemDTO dto;
	private ItemDTO otherDto;
	private ItemDTO inputDto;
	private ItemDTO outputDto;

	private int id;
	private List<ItemEntity> entityList;

	@BeforeEach
	void setUp() {
		entity = new ItemEntity();
		savedEntity = new ItemEntity();
		otherEntity = new ItemEntity();
		dto = new ItemDTO();
		otherDto = new ItemDTO();
		inputDto = new ItemDTO();
		outputDto = new ItemDTO();

		id = 1;
		entityList = List.of(entity, otherEntity);
	}

	// ===== SECTION 1 - CRUD API ONE ENTITY =====
	@Nested
	@DisplayName("SECTION 1 - CRUD API ONE ENTITY")
	class CrudApiOneEntity {

		@Nested
		@DisplayName("getItemById")
		class GetItemById {
			@Test
			@DisplayName("should return ItemDTO when id exists")
			void getItemById_success() {
				when(itemRepository.findById(id)).thenReturn(entity);
				when(itemMapper.toDTO(entity)).thenReturn(dto);

				ItemDTO result = itemService.getItemById(id);

				assertThat(result).isEqualTo(dto);
				verify(itemRepository).findById(id);
			}

			@Test
			@DisplayName("should throw NegativeIdentifierException when id is negative")
			void getItemById_negativeId() {
				assertThatThrownBy(() -> itemService.getItemById(-1)).isInstanceOf(NegativeIdentifierException.class);
			}

			@Test
			@DisplayName("should throw ObjectNotFoundException when id does not exist")
			void getItemById_notFound() {
				when(itemRepository.findById(id)).thenThrow(ObjectNotFoundException.newObjectNotFoundException(id));

				assertThatThrownBy(() -> itemService.getItemById(id)).isInstanceOf(ObjectNotFoundException.class);
			}
		}

		@Nested
		@DisplayName("createItem")
		class CreateItem {
			@Test
			@DisplayName("should create item successfully")
			void createItem_success() {
				inputDto.setName("New Item");

				when(itemRepository.existsByName(inputDto.getName())).thenReturn(false);
				when(itemMapper.toEntity(inputDto)).thenReturn(entity);
				when(itemRepository.save(entity)).thenReturn(savedEntity);
				when(itemMapper.toDTO(savedEntity)).thenReturn(outputDto);

				ItemDTO result = itemService.createItem(inputDto);

				assertThat(result).isEqualTo(outputDto);
				verify(itemRepository).save(entity);
			}

			@Test
			@DisplayName("should throw NullObjectException when DTO is null")
			void createItem_nullDto() {
				assertThatThrownBy(() -> itemService.createItem(null)).isInstanceOf(NullObjectException.class);
			}

			@Test
			@DisplayName("should throw NullStringParameterException when name is null or blank")
			void createItem_invalidName() {
				inputDto.setName(null);
				otherDto.setName(" ");

				assertThatThrownBy(() -> itemService.createItem(inputDto))
						.isInstanceOf(NullStringParameterException.class);
				assertThatThrownBy(() -> itemService.createItem(otherDto))
						.isInstanceOf(NullStringParameterException.class);
			}

			@Test
			@DisplayName("should throw ObjectAlreadyExistsException when name already exists")
			void createItem_alreadyExists() {
				dto.setName("Exists");
				when(itemRepository.existsByName(dto.getName())).thenReturn(true);

				assertThatThrownBy(() -> itemService.createItem(dto)).isInstanceOf(ObjectAlreadyExistsException.class);
			}
		}

		@Nested
		@DisplayName("updateItem")
		class UpdateItem {
			@Test
			@DisplayName("should update item successfully")
			void updateItem_success() {
				when(itemRepository.findById(id)).thenReturn(entity);
				when(itemRepository.save(entity)).thenReturn(savedEntity);
				when(itemMapper.toDTO(savedEntity)).thenReturn(outputDto);

				ItemDTO result = itemService.updateItem(id, inputDto);

				assertThat(result).isEqualTo(outputDto);
				verify(itemMapper).updateEntityFromDTO(inputDto, entity);
				verify(itemRepository).save(entity);
			}

			@Test
			@DisplayName("should throw ZeroUpdateIdentifierException when id is zero")
			void updateItem_zeroId() {
				assertThatThrownBy(() -> itemService.updateItem(0, dto))
						.isInstanceOf(ZeroUpdateIdentifierException.class);
			}

			@Test
			@DisplayName("should throw NegativeIdentifierException when id is negative")
			void updateItem_negativeId() {
				assertThatThrownBy(() -> itemService.updateItem(-1, dto))
						.isInstanceOf(NegativeIdentifierException.class);
			}

			@Test
			@DisplayName("should throw NullObjectException when DTO is null")
			void updateItem_nullDto() {
				assertThatThrownBy(() -> itemService.updateItem(id, null)).isInstanceOf(NullObjectException.class);
			}
		}

		@Nested
		@DisplayName("deleteItem")
		class DeleteItem {
			@Test
			@DisplayName("should delete item successfully")
			void deleteItem_success() {
				when(itemRepository.existsById(id)).thenReturn(true);

				itemService.deleteItem(id);

				verify(itemRepository).deleteById(id);
			}

			@Test
			@DisplayName("should throw ObjectNotFoundException when id does not exist")
			void deleteItem_notFound() {
				when(itemRepository.existsById(id)).thenReturn(false);

				assertThatThrownBy(() -> itemService.deleteItem(id)).isInstanceOf(ObjectNotFoundException.class);
			}

			@Test
			@DisplayName("should throw NegativeIdentifierException when id is negative")
			void deleteItem_negativeId() {
				assertThatThrownBy(() -> itemService.deleteItem(-1)).isInstanceOf(NegativeIdentifierException.class);
			}
		}
	}

	// ===== SECTION 2 - CRUD API MULTIPLE ENTITIES =====
	@Nested
	@DisplayName("SECTION 2 - CRUD API MULTIPLE ENTITIES")
	class CrudApiMultipleEntities {
		@Test
		@DisplayName("should return all items")
		void getAllItems_success() {
			when(itemRepository.findAll()).thenReturn(entityList);
			when(itemMapper.toDTO(entity)).thenReturn(dto);
			when(itemMapper.toDTO(otherEntity)).thenReturn(otherDto);

			List<ItemDTO> results = itemService.getAllItems();

			assertThat(results).containsExactly(dto, otherDto);
		}
	}
}
