package yonasazela.lahordeapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.services.IItemService;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

	private final IItemService itemService;

	// --- GET all items ---
	@GetMapping
	public ResponseEntity<List<ItemDTO>> getAllItems() {
		List<ItemDTO> items = itemService.getAllItems();
		return ResponseEntity.ok(items);
	}

	// --- GET item by ID ---
	@GetMapping("/{id}")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable int id) {
		ItemDTO item = itemService.getItemById(id);
		return ResponseEntity.ok(item);
	}

	// --- POST create new item ---
	@PostMapping
	public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO dto) {
		ItemDTO created = itemService.createItem(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	// --- PUT update existing item ---
	@PutMapping("/{id}")
	public ResponseEntity<ItemDTO> updateItem(@PathVariable int id, @RequestBody ItemDTO dto) {
		// Le service gère la validation de l'existence et le mapping sécurisé
		ItemDTO updated = itemService.updateItem(id, dto);
		return ResponseEntity.ok(updated);
	}

	// --- DELETE item ---
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable int id) {
		itemService.deleteItem(id);
		return ResponseEntity.noContent().build();
	}
}
