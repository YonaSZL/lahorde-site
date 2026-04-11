package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ObjectAlreadyExistsExceptionTest extends ExceptionsBaseTest {

	@Test
    void shouldThrow_ObjectAlreadyExistsException_WhenNameAlreadyTaken() {
        when(itemRepository.existsByName(validName)).thenReturn(true);
        assertThrows(ObjectAlreadyExistsException.class, () -> itemService.createItem(itemDTO));
    }

	@Test
    void shouldNotThrow_ObjectAlreadyExistsException_WhenNameIsNew() {
        when(itemRepository.existsByName(validName)).thenReturn(false);
        when(itemMapper.toEntity(itemDTO)).thenReturn(itemEntity);
        assertDoesNotThrow(() -> itemService.createItem(itemDTO));
    }
}
