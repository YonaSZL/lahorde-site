package yonasazela.lahordeapi.services;

import yonasazela.lahordeapi.dto.ItemDTO;

import java.util.List;

/**
 * Service interface for managing items.
 * Defines the contract for item-related business logic.
 */
public interface IItemService {

	/**
	 * Retrieves all items.
	 *
	 * @return a list of ItemDTO objects.
	 */
	List<ItemDTO> getAllItems();

	/**
	 * Retrieves an item by its ID.
	 *
	 * @param id the ID of the item to retrieve.
	 * @return the found ItemDTO.
	 */
	ItemDTO getItemById(int id);

	/**
	 * Creates a new item.
	 *
	 * @param dto the ItemDTO containing the details of the item to create.
	 * @return the created ItemDTO.
	 */
	ItemDTO createItem(ItemDTO dto);

	/**
	 * Updates an existing item.
	 *
	 * @param id  the ID of the item to update.
	 * @param dto the ItemDTO containing the new details.
	 * @return the updated ItemDTO.
	 */
	ItemDTO updateItem(int id, ItemDTO dto);

	/**
	 * Deletes an item by its ID.
	 *
	 * @param id the ID of the item to delete.
	 */
	void deleteItem(int id);
}
