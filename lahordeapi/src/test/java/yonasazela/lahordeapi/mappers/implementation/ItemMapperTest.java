package yonasazela.lahordeapi.mappers.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.NullObjectException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemMapperTest {

	private ItemMapper itemMapper;
	private ItemEntity validEntity;
	private ItemDTO validDTO;
	private ItemEntity entityToUpdate;
	private ItemEntity nullSafeEntity;
	private ItemDTO nullSafeDTO;

	@BeforeEach
	void setUp() {
		itemMapper = ItemMapper.newItemMapper();

		validEntity = ItemEntity.builder().id(1).name("Item 1").price(new BigDecimal("10.000"))
				.weight(new BigDecimal("1.00")).size(2).priceForOneSlot(new BigDecimal("5.000"))
				.description("Description 1").build();

		validDTO = ItemDTO.builder().id(1).name("Item 1").price(new BigDecimal("10.000")).weight(new BigDecimal("1.00"))
				.size(2).priceForOneSlot(new BigDecimal("5.000")).description("Description 1").build();

		entityToUpdate = ItemEntity.builder().id(1).name("Old Name").price(new BigDecimal("10.000"))
				.weight(new BigDecimal("1.00")).size(2).description("Old Description").build();

		nullSafeEntity = new ItemEntity();
		nullSafeDTO = new ItemDTO();
	}

	@Test
	@DisplayName("should map ItemEntity to ItemDTO")
	void toDTO() {
		ItemDTO dto = itemMapper.toDTO(validEntity);

		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(validEntity.getId());
		assertThat(dto.getName()).isEqualTo(validEntity.getName());
		assertThat(dto.getPrice()).isEqualTo(validEntity.getPrice());
		assertThat(dto.getWeight()).isEqualTo(validEntity.getWeight());
		assertThat(dto.getSize()).isEqualTo(validEntity.getSize());
		assertThat(dto.getPriceForOneSlot()).isEqualTo(validEntity.getPriceForOneSlot());
		assertThat(dto.getDescription()).isEqualTo(validEntity.getDescription());
	}

	@Test
	@DisplayName("should throw NullObjectException when mapping null ItemEntity to DTO")
	void toDTO_null() {
		assertThatThrownBy(() -> itemMapper.toDTO(null)).isInstanceOf(NullObjectException.class);
	}

	@Test
	@DisplayName("should map ItemDTO to ItemEntity")
	void toEntity() {
		ItemEntity entity = itemMapper.toEntity(validDTO);

		assertThat(entity).isNotNull();
		assertThat(entity.getId()).isEqualTo(validDTO.getId());
		assertThat(entity.getName()).isEqualTo(validDTO.getName());
		assertThat(entity.getPrice()).isEqualTo(validDTO.getPrice());
		assertThat(entity.getWeight()).isEqualTo(validDTO.getWeight());
		assertThat(entity.getSize()).isEqualTo(validDTO.getSize());
		assertThat(entity.getDescription()).isEqualTo(validDTO.getDescription());
		// PriceForOneSlot should not be mapped from DTO to Entity as it's DB generated
		assertThat(entity.getPriceForOneSlot()).isNull();
	}

	@Test
	@DisplayName("should throw NullObjectException when mapping null ItemDTO to Entity")
	void toEntity_null() {
		assertThatThrownBy(() -> itemMapper.toEntity(null)).isInstanceOf(NullObjectException.class);
	}

	@Test
	@DisplayName("should update ItemEntity from ItemDTO")
	void updateEntityFromDTO() {
		itemMapper.updateEntityFromDTO(validDTO, entityToUpdate);

		assertThat(entityToUpdate.getId()).isEqualTo(1); // ID should not change
		assertThat(entityToUpdate.getName()).isEqualTo(validDTO.getName());
		assertThat(entityToUpdate.getPrice()).isEqualTo(validDTO.getPrice());
		assertThat(entityToUpdate.getWeight()).isEqualTo(validDTO.getWeight());
		assertThat(entityToUpdate.getSize()).isEqualTo(validDTO.getSize());
		assertThat(entityToUpdate.getDescription()).isEqualTo(validDTO.getDescription());
	}

	@Test
	@DisplayName("should throw NullObjectException when updating with null DTO or Entity")
	void updateEntityFromDTO_null() {
		assertThatThrownBy(() -> itemMapper.updateEntityFromDTO(null, nullSafeEntity))
				.isInstanceOf(NullObjectException.class);
		assertThatThrownBy(() -> itemMapper.updateEntityFromDTO(nullSafeDTO, null))
				.isInstanceOf(NullObjectException.class);
	}
}
