package yonasazela.lahordeapi.services;

import yonasazela.lahordeapi.dto.ItemDTO;

import java.util.List;

public interface IItemService {

    List<ItemDTO> getAllItems();

    ItemDTO getItemById(int id);

    ItemDTO createItem(ItemDTO dto); // Insert

    ItemDTO updateItem(int id, ItemDTO dto); // Update sécurisé

    void deleteItem(int id);
}
