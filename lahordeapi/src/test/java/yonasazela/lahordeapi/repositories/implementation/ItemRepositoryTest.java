package yonasazela.lahordeapi.repositories.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yonasazela.lahordeapi.kenshi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.DBException;
import yonasazela.lahordeapi.exceptions.ObjectNotFoundException;
import yonasazela.lahordeapi.kenshi.repositories.IItemRepository;
import yonasazela.lahordeapi.kenshi.repositories.implementation.ItemRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryTest {

	@Mock
	private IItemRepository iItemRepository;

	private ItemRepository itemRepository;

	@BeforeEach
	void setUp() {
		itemRepository = new ItemRepository(iItemRepository);
	}

	@Nested
	@DisplayName("SECTION 1 - CRUD DATABASE ONE ENTITY")
	class CrudOneEntity {

		@Nested
		@DisplayName("save")
		class Save {
			@Test
			@DisplayName("should save entity successfully")
			void save_success() {
				ItemEntity entity = new ItemEntity();
				when(iItemRepository.save(entity)).thenReturn(entity);

				ItemEntity result = itemRepository.save(entity);

				assertThat(result).isEqualTo(entity);
				verify(iItemRepository).save(entity);
			}

			@Test
			@DisplayName("should throw DBException when save fails")
			void save_failure() {
				ItemEntity entity = new ItemEntity();
				RuntimeException cause = new RuntimeException("DB error");
				when(iItemRepository.save(entity)).thenThrow(cause);

				assertThatThrownBy(() -> itemRepository.save(entity)).isInstanceOf(DBException.class)
						.hasMessageContaining("Error while saving object").hasCause(cause);
			}
		}

		@Nested
		@DisplayName("findById")
		class FindById {
			@Test
			@DisplayName("should return entity when it exists")
			void findById_success() {
				int id = 1;
				ItemEntity entity = new ItemEntity();
				when(iItemRepository.findById(id)).thenReturn(Optional.of(entity));

				ItemEntity result = itemRepository.findById(id);

				assertThat(result).isEqualTo(entity);
				verify(iItemRepository).findById(id);
			}

			@Test
			@DisplayName("should throw ObjectNotFoundException when entity does not exist")
			void findById_notFound() {
				int id = 1;
				when(iItemRepository.findById(id)).thenReturn(Optional.empty());

				assertThatThrownBy(() -> itemRepository.findById(id)).isInstanceOf(ObjectNotFoundException.class)
						.hasMessageContaining("Object not found with id : " + id);
			}

			@Test
			@DisplayName("should throw DBException when findById fails")
			void findById_failure() {
				int id = 1;
				RuntimeException cause = new RuntimeException("DB error");
				when(iItemRepository.findById(id)).thenThrow(cause);

				assertThatThrownBy(() -> itemRepository.findById(id)).isInstanceOf(DBException.class)
						.hasMessageContaining("Error while retrieving object: " + id).hasCause(cause);
			}
		}

		@Nested
		@DisplayName("existsById")
		class ExistsById {
			@Test
			@DisplayName("should return true when entity exists")
			void existsById_true() {
				int id = 1;
				when(iItemRepository.existsById(id)).thenReturn(true);

				boolean result = itemRepository.existsById(id);

				assertThat(result).isTrue();
				verify(iItemRepository).existsById(id);
			}

			@Test
			@DisplayName("should return false when entity does not exist")
			void existsById_false() {
				int id = 1;
				when(iItemRepository.existsById(id)).thenReturn(false);

				boolean result = itemRepository.existsById(id);

				assertThat(result).isFalse();
				verify(iItemRepository).existsById(id);
			}

			@Test
			@DisplayName("should throw DBException when existsById fails")
			void existsById_failure() {
				int id = 1;
				RuntimeException cause = new RuntimeException("DB error");
				when(iItemRepository.existsById(id)).thenThrow(cause);

				assertThatThrownBy(() -> itemRepository.existsById(id)).isInstanceOf(DBException.class)
						.hasMessageContaining("Error while checking existence: " + id).hasCause(cause);
			}
		}

		@Nested
		@DisplayName("existsByName")
		class ExistsByName {
			@Test
			@DisplayName("should return true when entity name exists")
			void existsByName_true() {
				String name = "Test Item";
				when(iItemRepository.existsByName(name)).thenReturn(true);

				boolean result = itemRepository.existsByName(name);

				assertThat(result).isTrue();
				verify(iItemRepository).existsByName(name);
			}

			@Test
			@DisplayName("should throw DBException when existsByName fails")
			void existsByName_failure() {
				String name = "Test Item";
				RuntimeException cause = new RuntimeException("DB error");
				when(iItemRepository.existsByName(name)).thenThrow(cause);

				assertThatThrownBy(() -> itemRepository.existsByName(name)).isInstanceOf(DBException.class)
						.hasMessageContaining("Error while checking existence: " + name).hasCause(cause);
			}
		}

		@Nested
		@DisplayName("deleteById")
		class DeleteById {
			@Test
			@DisplayName("should delete entity successfully")
			void deleteById_success() {
				int id = 1;
				doNothing().when(iItemRepository).deleteById(id);

				itemRepository.deleteById(id);

				verify(iItemRepository).deleteById(id);
			}

			@Test
			@DisplayName("should throw DBException when deleteById fails")
			void deleteById_failure() {
				int id = 1;
				RuntimeException cause = new RuntimeException("DB error");
				doThrow(cause).when(iItemRepository).deleteById(id);

				assertThatThrownBy(() -> itemRepository.deleteById(id)).isInstanceOf(DBException.class)
						.hasMessageContaining("Error while deleting object: " + id).hasCause(cause);
			}
		}
	}

	@Nested
	@DisplayName("SECTION 2 - CRUD DATABASE MULTIPLE ENTITIES")
	class CrudMultipleEntities {

		@Nested
		@DisplayName("findAll")
		class FindAll {
			@Test
			@DisplayName("should return all entities successfully")
			void findAll_success() {
				List<ItemEntity> entities = List.of(new ItemEntity(), new ItemEntity());
				when(iItemRepository.findAll()).thenReturn(entities);

				List<ItemEntity> result = itemRepository.findAll();

				assertThat(result).hasSize(2).isEqualTo(entities);
				verify(iItemRepository).findAll();
			}

			@Test
			@DisplayName("should throw DBException when findAll fails")
			void findAll_failure() {
				RuntimeException cause = new RuntimeException("DB error");
				when(iItemRepository.findAll()).thenThrow(cause);

				assertThatThrownBy(() -> itemRepository.findAll()).isInstanceOf(DBException.class)
						.hasMessageContaining("Error while retrieving all objects").hasCause(cause);
			}
		}
	}
}
