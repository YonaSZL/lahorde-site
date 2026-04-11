package yonasazela.lahordeapi.kenshi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yonasazela.lahordeapi.kenshi.dto.ItemDTO;
import yonasazela.lahordeapi.kenshi.services.IItemService;

import java.util.List;

/**
 * REST controller for managing items in the Lahorde API. Provides endpoints for
 * CRUD operations on items.
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

	private final IItemService itemService;

	/**
	 * Retrieves all items.
	 *
	 * @return a list of ItemDTO objects.
	 */
	@GetMapping
	public ResponseEntity<List<ItemDTO>> getAllItems() {
		List<ItemDTO> items = itemService.getAllItems();
		return ResponseEntity.ok(items);
	}

	/**
	 * Retrieves an item by its ID.
	 *
	 * @param id
	 *            the ID of the item to retrieve.
	 * @return the found ItemDTO object.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable int id) {
		ItemDTO item = itemService.getItemById(id);
		return ResponseEntity.ok(item);
	}

	/**
	 * Creates a new item.
	 *
	 * @param dto
	 *            the ItemDTO object containing the details of the item to create.
	 * @return the created ItemDTO object.
	 */
	@PostMapping
	public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO dto) {
		ItemDTO created = itemService.createItem(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	/**
	 * Updates an existing item.
	 *
	 * @param id
	 *            the ID of the item to update.
	 * @param dto
	 *            the ItemDTO object containing the new details for the item.
	 * @return the updated ItemDTO object.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ItemDTO> updateItem(@PathVariable int id, @RequestBody ItemDTO dto) {
		ItemDTO updated = itemService.updateItem(id, dto);
		return ResponseEntity.ok(updated);
	}

	/**
	 * Deletes an item by its ID.
	 *
	 * @param id
	 *            the ID of the item to delete.
	 * @return a ResponseEntity with no content.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable int id) {
		itemService.deleteItem(id);
		return ResponseEntity.noContent().build();
	}
}
