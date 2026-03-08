package yonasazela.lahordeapi.services.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.*;
import yonasazela.lahordeapi.mappers.implementation.ItemMapper;
import yonasazela.lahordeapi.repositories.ItemRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private ItemMapper itemMapper;

	@InjectMocks
	private ItemService itemService;

	// ===== SECTION 1 - CRUD DATABASE ONE ENTITY =====
	@Nested
	@DisplayName("SECTION 1 - CRUD DATABASE ONE ENTITY")
	class CrudDatabaseOneEntity {
		@Test
		@DisplayName("findById should throw DBException when repository fails")
		void findById_dbException() {
			int id = 1;
			when(itemRepository.findById(id)).thenThrow(new RuntimeException("DB error"));

			assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(itemService, "findById", id))
					.isInstanceOf(DBException.class).hasMessageContaining("Error while retrieving object");
		}

		@Test
		@DisplayName("save should throw DBException when repository fails")
		void save_dbException() {
			ItemEntity entity = new ItemEntity();
			when(itemRepository.save(any())).thenThrow(new RuntimeException("DB error"));

			assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(itemService, "save", entity))
					.isInstanceOf(DBException.class).hasMessageContaining("Error while saving object");
		}

		@Test
		@DisplayName("existsById should throw DBException when repository fails")
		void existsById_dbException() {
			int id = 1;
			when(itemRepository.existsById(id)).thenThrow(new RuntimeException("DB error"));

			assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(itemService, "existsById", id))
					.isInstanceOf(DBException.class).hasMessageContaining("Error while checking existence");
		}

		@Test
		@DisplayName("existsByName should throw DBException when repository fails")
		void existsByName_dbException() {
			String name = "Item";
			when(itemRepository.existsByName(name)).thenThrow(new RuntimeException("DB error"));

			assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(itemService, "existsByName", name))
					.isInstanceOf(DBException.class).hasMessageContaining("Error while checking existence");
		}

		@Test
		@DisplayName("deleteById should throw DBException when repository fails")
		void deleteById_dbException() {
			int id = 1;
			doThrow(new RuntimeException("DB error")).when(itemRepository).deleteById(id);

			assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(itemService, "deleteById", id))
					.isInstanceOf(DBException.class).hasMessageContaining("Error while deleting object");
		}
	}

	// ===== SECTION 2 - CRUD DATABASE MULTIPLE ENTITIES =====
	@Nested
	@DisplayName("SECTION 2 - CRUD DATABASE MULTIPLE ENTITIES")
	class CrudDatabaseMultipleEntities {
		@Test
        @DisplayName("findAll should throw DBException when repository fails")
        void findAll_dbException() {
            when(itemRepository.findAll()).thenThrow(new RuntimeException("DB error"));

            assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(itemService, "findAll"))
                    .isInstanceOf(DBException.class)
                    .hasMessageContaining("Error while retrieving all objects");
        }
	}

	// ===== SECTION 3 - CRUD API ONE ENTITY =====
	@Nested
	@DisplayName("SECTION 3 - CRUD API ONE ENTITY")
	class CrudApiOneEntity {

		@Nested
		@DisplayName("getItemById")
		class GetItemById {
			@Test
			@DisplayName("should return ItemDTO when id exists")
			void getItemById_success() {
				int id = 1;
				ItemEntity entity = new ItemEntity();
				ItemDTO dto = new ItemDTO();
				when(itemRepository.findById(id)).thenReturn(Optional.of(entity));
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
				int id = 1;
				when(itemRepository.findById(id)).thenReturn(Optional.empty());

				assertThatThrownBy(() -> itemService.getItemById(id)).isInstanceOf(ObjectNotFoundException.class);
			}
		}

		@Nested
		@DisplayName("createItem")
		class CreateItem {
			@Test
			@DisplayName("should create item successfully")
			void createItem_success() {
				ItemDTO inputDto = ItemDTO.builder().name("New Item").build();
				ItemEntity entity = new ItemEntity();
				ItemEntity savedEntity = new ItemEntity();
				ItemDTO outputDto = new ItemDTO();

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
				ItemDTO nullName = ItemDTO.builder().name(null).build();
				ItemDTO blankName = ItemDTO.builder().name(" ").build();

				assertThatThrownBy(() -> itemService.createItem(nullName))
						.isInstanceOf(NullStringParameterException.class);
				assertThatThrownBy(() -> itemService.createItem(blankName))
						.isInstanceOf(NullStringParameterException.class);
			}

			@Test
			@DisplayName("should throw ObjectAlreadyExistsException when name already exists")
			void createItem_alreadyExists() {
				ItemDTO dto = ItemDTO.builder().name("Exists").build();
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
				int id = 1;
				ItemDTO inputDto = new ItemDTO();
				ItemEntity existingEntity = new ItemEntity();
				ItemEntity savedEntity = new ItemEntity();
				ItemDTO outputDto = new ItemDTO();

				when(itemRepository.findById(id)).thenReturn(Optional.of(existingEntity));
				when(itemRepository.save(existingEntity)).thenReturn(savedEntity);
				when(itemMapper.toDTO(savedEntity)).thenReturn(outputDto);

				ItemDTO result = itemService.updateItem(id, inputDto);

				assertThat(result).isEqualTo(outputDto);
				verify(itemMapper).updateEntityFromDTO(inputDto, existingEntity);
				verify(itemRepository).save(existingEntity);
			}

			@Test
			@DisplayName("should throw ZeroUpdateIdentifierException when id is zero")
			void updateItem_zeroId() {
				ItemDTO dto = new ItemDTO();

				assertThatThrownBy(() -> itemService.updateItem(0, dto))
						.isInstanceOf(ZeroUpdateIdentifierException.class);
			}

			@Test
			@DisplayName("should throw NegativeIdentifierException when id is negative")
			void updateItem_negativeId() {
				ItemDTO dto = new ItemDTO();

				assertThatThrownBy(() -> itemService.updateItem(-1, dto))
						.isInstanceOf(NegativeIdentifierException.class);
			}
		}

		@Nested
		@DisplayName("deleteItem")
		class DeleteItem {
			@Test
			@DisplayName("should delete item successfully")
			void deleteItem_success() {
				int id = 1;
				when(itemRepository.existsById(id)).thenReturn(true);

				itemService.deleteItem(id);

				verify(itemRepository).deleteById(id);
			}

			@Test
			@DisplayName("should throw ObjectNotFoundException when id does not exist")
			void deleteItem_notFound() {
				int id = 1;
				when(itemRepository.existsById(id)).thenReturn(false);

				assertThatThrownBy(() -> itemService.deleteItem(id)).isInstanceOf(ObjectNotFoundException.class);
			}
		}
	}

	// ===== SECTION 4 - CRUD API MULTIPLE ENTITIES =====
	@Nested
	@DisplayName("SECTION 4 - CRUD API MULTIPLE ENTITIES")
	class CrudApiMultipleEntities {
		@Test
		@DisplayName("should return all items")
		void getAllItems_success() {
			ItemEntity entity1 = new ItemEntity();
			ItemEntity entity2 = new ItemEntity();
			List<ItemEntity> entities = List.of(entity1, entity2);
			ItemDTO dto1 = new ItemDTO();
			ItemDTO dto2 = new ItemDTO();

			when(itemRepository.findAll()).thenReturn(entities);
			when(itemMapper.toDTO(entity1)).thenReturn(dto1);
			when(itemMapper.toDTO(entity2)).thenReturn(dto2);

			List<ItemDTO> results = itemService.getAllItems();

			assertThat(results).containsExactly(dto1, dto2);
		}
	}
}
