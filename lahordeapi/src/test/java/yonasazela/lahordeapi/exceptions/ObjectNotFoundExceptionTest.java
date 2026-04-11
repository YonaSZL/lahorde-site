package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ObjectNotFoundExceptionTest extends ExceptionsBaseTest {

	@Test
    void shouldThrow_ObjectNotFoundException_WhenIdDoesNotExist() {
        when(itemRepository.findById(id)).thenThrow(ObjectNotFoundException.newObjectNotFoundException(id));
        assertThrows(ObjectNotFoundException.class, () -> itemService.getItemById(1));
    }

	@Test
    void shouldNotThrow_ObjectNotFoundException_WhenIdExists() {
        when(itemRepository.findById(id)).thenReturn(itemEntity);
        assertDoesNotThrow(() -> itemService.getItemById(1));
    }
}
