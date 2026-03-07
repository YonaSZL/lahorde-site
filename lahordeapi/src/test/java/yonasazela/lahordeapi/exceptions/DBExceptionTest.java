package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DBExceptionTest extends ExceptionsBaseTest {

    @Test
    void shouldThrow_DBException_WhenDatabaseCrashesOnSave() {
        when(itemRepository.findById(1)).thenThrow(new RuntimeException("DB crash"));
        assertThrows(DBException.class, () -> itemService.getItemById(1));
    }

    @Test
    void shouldNotThrow_DBException_WhenDatabaseWorks() {
        when(itemRepository.findById(1)).thenReturn(Optional.of(itemEntity));
        assertDoesNotThrow(() -> itemService.getItemById(1));
    }
}
