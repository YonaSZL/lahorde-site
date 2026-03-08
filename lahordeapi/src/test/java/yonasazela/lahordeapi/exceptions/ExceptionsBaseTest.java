package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;
import yonasazela.lahordeapi.mappers.implementation.ItemMapper;
import yonasazela.lahordeapi.repositories.ItemRepository;
import yonasazela.lahordeapi.services.implementation.ItemService;

@ExtendWith(MockitoExtension.class)
abstract class ExceptionsBaseTest {

	@Mock
	protected ItemRepository itemRepository;

	@Mock
	protected ItemMapper itemMapper;

	@InjectMocks
	protected ItemService itemService;

	protected ItemEntity itemEntity;
	protected ItemDTO itemDTO;
	protected int id = 1;
	protected String validName = "Valid Name";

	@BeforeEach
	void setUp() {
		itemEntity = new ItemEntity();
		itemDTO = ItemDTO.builder().name(validName).build();
	}
}
