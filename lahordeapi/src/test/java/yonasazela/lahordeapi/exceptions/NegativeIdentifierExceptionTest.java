package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class NegativeIdentifierExceptionTest extends ExceptionsBaseTest {

	@Test
	void shouldThrow_NegativeIdentifierException_WhenIdIsNegative() {
		assertThrows(NegativeIdentifierException.class, () -> itemService.getItemById(-1));
	}

	@Test
    void shouldNotThrow_NegativeIdentifierException_WhenIdIsPositive() {
        when(itemRepository.findById(id)).thenReturn(itemEntity);
        assertDoesNotThrow(() -> itemService.getItemById(1));
    }
}
