package yonasazela.lahordeapi.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yonasazela.lahordeapi.kenshi.dto.ItemDTO;
import yonasazela.lahordeapi.kenshi.entities.ItemEntity;
import yonasazela.lahordeapi.kenshi.mappers.implementation.ItemMapper;
import yonasazela.lahordeapi.kenshi.repositories.implementation.ItemRepository;
import yonasazela.lahordeapi.kenshi.services.implementation.ItemService;

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
