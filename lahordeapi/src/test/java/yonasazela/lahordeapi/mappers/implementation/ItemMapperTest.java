package yonasazela.lahordeapi.mappers.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ItemMapperTest {

	private ItemMapper itemMapper;

	@BeforeEach
	void setUp() {
		itemMapper = new ItemMapper();
	}

	@Test
	@DisplayName("should map ItemEntity to ItemDTO")
	void toDTO() {
		// Given
		ItemEntity entity = ItemEntity.builder().id(1).name("Item 1").price(new BigDecimal("10.000"))
				.weight(new BigDecimal("1.00")).size(2).priceForOneSlot(new BigDecimal("5.000"))
				.description("Description 1").build();

		// When
		ItemDTO dto = itemMapper.toDTO(entity);

		// Then
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(entity.getId());
		assertThat(dto.getName()).isEqualTo(entity.getName());
		assertThat(dto.getPrice()).isEqualTo(entity.getPrice());
		assertThat(dto.getWeight()).isEqualTo(entity.getWeight());
		assertThat(dto.getSize()).isEqualTo(entity.getSize());
		assertThat(dto.getPriceForOneSlot()).isEqualTo(entity.getPriceForOneSlot());
		assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
	}

	@Test
	@DisplayName("should return null when mapping null ItemEntity to DTO")
	void toDTO_null() {
		assertThat(itemMapper.toDTO(null)).isNull();
	}

	@Test
	@DisplayName("should map ItemDTO to ItemEntity")
	void toEntity() {
		// Given
		ItemDTO dto = ItemDTO.builder().id(1).name("Item 1").price(new BigDecimal("10.000"))
				.weight(new BigDecimal("1.00")).size(2).description("Description 1").build();

		// When
		ItemEntity entity = itemMapper.toEntity(dto);

		// Then
		assertThat(entity).isNotNull();
		assertThat(entity.getId()).isEqualTo(dto.getId());
		assertThat(entity.getName()).isEqualTo(dto.getName());
		assertThat(entity.getPrice()).isEqualTo(dto.getPrice());
		assertThat(entity.getWeight()).isEqualTo(dto.getWeight());
		assertThat(entity.getSize()).isEqualTo(dto.getSize());
		assertThat(entity.getDescription()).isEqualTo(dto.getDescription());
		// PriceForOneSlot should not be mapped from DTO to Entity as it's DB generated
		assertThat(entity.getPriceForOneSlot()).isNull();
	}

	@Test
	@DisplayName("should return null when mapping null ItemDTO to Entity")
	void toEntity_null() {
		assertThat(itemMapper.toEntity(null)).isNull();
	}

	@Test
	@DisplayName("should update ItemEntity from ItemDTO")
	void updateEntityFromDTO() {
		// Given
		ItemEntity entity = ItemEntity.builder().id(1).name("Old Name").price(new BigDecimal("10.000"))
				.weight(new BigDecimal("1.00")).size(2).description("Old Description").build();

		ItemDTO dto = ItemDTO.builder().name("New Name").price(new BigDecimal("20.000")).weight(new BigDecimal("2.00"))
				.size(4).description("New Description").build();

		// When
		itemMapper.updateEntityFromDTO(dto, entity);

		// Then
		assertThat(entity.getId()).isEqualTo(1); // ID should not change
		assertThat(entity.getName()).isEqualTo("New Name");
		assertThat(entity.getPrice()).isEqualTo(new BigDecimal("20.000"));
		assertThat(entity.getWeight()).isEqualTo(new BigDecimal("2.00"));
		assertThat(entity.getSize()).isEqualTo(4);
		assertThat(entity.getDescription()).isEqualTo("New Description");
	}

	@Test
	@DisplayName("should do nothing when updating with null DTO or Entity")
	void updateEntityFromDTO_null() {
		ItemEntity entity = new ItemEntity();
		itemMapper.updateEntityFromDTO(null, entity);
		assertThat(entity).isEqualTo(new ItemEntity());

		ItemDTO dto = new ItemDTO();
		itemMapper.updateEntityFromDTO(dto, null);
		assertThat(dto).isEqualTo(new ItemDTO());
	}
}
