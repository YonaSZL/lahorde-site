package yonasazela.lahordeapi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import yonasazela.lahordeapi.kenshi.controllers.ItemController;
import yonasazela.lahordeapi.kenshi.dto.ItemDTO;
import yonasazela.lahordeapi.kenshi.services.IItemService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for ItemController. Tests the controller in a mock MVC environment
 * using MockMvc.
 */
@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
	private static final String API_ROOT = "/api/";
	private static final String ITEMS_URL = API_ROOT + "items";
	private static final String ITEM_BY_ID_URL = ITEMS_URL + "/{id}";

	private MockMvc mockMvc;

	@Mock
	private IItemService itemService;

	@InjectMocks
	private ItemController itemController;

	private ItemDTO itemDTO;
	private List<ItemDTO> itemDTOs;
	private int id;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
		id = 1;
		itemDTO = ItemDTO.builder().id(id).name("Test Item").price(BigDecimal.valueOf(10.0))
				.weight(BigDecimal.valueOf(1.0)).size(1).description("Test Description").build();
		itemDTOs = List.of(itemDTO);
	}

	private String asJsonString() {
		return "{\"id\":" + id
				+ ",\"name\":\"Test Item\",\"price\":10.0,\"weight\":1.0,\"size\":1,\"description\":\"Test Description\"}";
	}

	@Nested
	@DisplayName("GET " + ITEMS_URL)
	class GetAllItems {
		@Test
		@DisplayName("should return all items")
		void getAllItems_success() throws Exception {
			when(itemService.getAllItems()).thenReturn(itemDTOs);

			mockMvc.perform(get(ITEMS_URL)).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].id").value(id))
					.andExpect(jsonPath("$[0].name").value("Test Item"));

			verify(itemService).getAllItems();
		}
	}

	@Nested
	@DisplayName("GET " + ITEM_BY_ID_URL)
	class GetItemById {
		@Test
		@DisplayName("should return item by id")
		void getItemById_success() throws Exception {
			when(itemService.getItemById(id)).thenReturn(itemDTO);

			mockMvc.perform(get(ITEM_BY_ID_URL, id)).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(id))
					.andExpect(jsonPath("$.name").value("Test Item"));

			verify(itemService).getItemById(id);
		}
	}

	@Nested
	@DisplayName("POST " + ITEMS_URL)
	class CreateItem {
		@Test
		@DisplayName("should create a new item")
		void createItem_success() throws Exception {
			when(itemService.createItem(any(ItemDTO.class))).thenReturn(itemDTO);

			mockMvc.perform(post(ITEMS_URL).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString())).andExpect(status().isCreated())
					.andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.name").value("Test Item"));

			verify(itemService).createItem(any(ItemDTO.class));
		}
	}

	@Nested
	@DisplayName("PUT " + ITEM_BY_ID_URL)
	class UpdateItem {
		@Test
		@DisplayName("should update an existing item")
		void updateItem_success() throws Exception {
			when(itemService.updateItem(eq(id), any(ItemDTO.class))).thenReturn(itemDTO);

			mockMvc.perform(put(ITEM_BY_ID_URL, id).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString())).andExpect(status().isOk())
					.andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.name").value("Test Item"));

			verify(itemService).updateItem(eq(id), any(ItemDTO.class));
		}
	}

	@Nested
	@DisplayName("DELETE " + ITEM_BY_ID_URL)
	class DeleteItem {
		@Test
		@DisplayName("should delete an item by id")
		void deleteItem_success() throws Exception {
			doNothing().when(itemService).deleteItem(id);

			mockMvc.perform(delete(ITEM_BY_ID_URL, id)).andExpect(status().isNoContent());

			verify(itemService).deleteItem(id);
		}
	}
}
