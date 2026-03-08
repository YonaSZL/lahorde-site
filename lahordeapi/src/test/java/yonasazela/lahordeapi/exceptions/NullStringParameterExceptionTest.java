package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Test;
import yonasazela.lahordeapi.dto.ItemDTO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class NullStringParameterExceptionTest extends ExceptionsBaseTest {

	@Test
	void shouldThrow_NullStringParameterException_WhenNameIsNull() {
		ItemDTO dto = ItemDTO.builder().name(null).build();
		assertThrows(NullStringParameterException.class, () -> itemService.createItem(dto));
	}

	@Test
	void shouldThrow_NullStringParameterException_WhenNameIsBlank() {
		ItemDTO dto = ItemDTO.builder().name("  ").build();
		assertThrows(NullStringParameterException.class, () -> itemService.createItem(dto));
	}

	@Test
    void shouldNotThrow_NullStringParameterException_WhenNameIsValid() {
        when(itemMapper.toEntity(itemDTO)).thenReturn(itemEntity);
        assertDoesNotThrow(() -> itemService.createItem(itemDTO));
    }
}
