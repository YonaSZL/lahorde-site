package yonasazela.lahordeapi.kenshi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yonasazela.lahordeapi.kenshi.entities.ItemEntity;

/**
 * Repository interface for ItemEntity. Provides standard CRUD operations and
 * custom query methods.
 */
@Repository
public interface IItemRepository extends JpaRepository<ItemEntity, Integer> {
	/**
	 * Checks if an item exists by its name.
	 *
	 * @param name
	 *            the name of the item.
	 * @return true if an item with the given name exists, false otherwise.
	 */
	boolean existsByName(String name);
}
