package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DBExceptionTest extends ExceptionsBaseTest {

	@Test
    void shouldThrow_DBException_WhenDatabaseCrashesOnSave() {
        when(itemRepository.findById(id)).thenThrow(DBException.newDBException("action", new RuntimeException("DB crash")));
        assertThrows(DBException.class, () -> itemService.getItemById(1));
    }

	@Test
    void shouldNotThrow_DBException_WhenDatabaseWorks() {
        when(itemRepository.findById(id)).thenReturn(itemEntity);
        assertDoesNotThrow(() -> itemService.getItemById(1));
    }

	@Test
	void shouldHaveCorrectMessageWithArgument() {
		DBException ex = DBException.newDBException("action", "arg", new RuntimeException());
		assertThat(ex.getMessage()).isEqualTo("Error while action: arg");

		DBException exNoArg = DBException.newDBException("action", null, new RuntimeException());
		assertThat(exNoArg.getMessage()).isEqualTo("Error while action");
	}
}
