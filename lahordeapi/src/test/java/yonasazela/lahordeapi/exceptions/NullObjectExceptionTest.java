package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class NullObjectExceptionTest extends ExceptionsBaseTest {

	@Test
	void shouldThrow_NullObjectException_WhenDTOIsNull() {
		assertThrows(NullObjectException.class, () -> itemService.createItem(null));
	}

	@Test
    void shouldNotThrow_NullObjectException_WhenDTOIsNotNull() {
        when(itemMapper.toEntity(itemDTO)).thenReturn(itemEntity);
        assertDoesNotThrow(() -> itemService.createItem(itemDTO));
    }
}
