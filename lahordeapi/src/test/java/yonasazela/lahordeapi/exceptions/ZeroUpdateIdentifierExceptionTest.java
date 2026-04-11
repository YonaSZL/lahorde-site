package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ZeroUpdateIdentifierExceptionTest extends ExceptionsBaseTest {

	@Test
	void shouldThrow_ZeroUpdateIdentifierException_WhenIdIsZeroForUpdate() {
		assertThrows(ZeroUpdateIdentifierException.class, () -> itemService.updateItem(0, itemDTO));
	}

	@Test
    void shouldNotThrow_ZeroUpdateIdentifierException_WhenIdIsNotZeroForUpdate() {
        when(itemRepository.findById(id)).thenReturn(itemEntity);
        assertDoesNotThrow(() -> itemService.updateItem(1, itemDTO));
    }
}
