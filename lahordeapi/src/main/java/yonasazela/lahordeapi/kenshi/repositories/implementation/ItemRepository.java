package yonasazela.lahordeapi.kenshi.repositories.implementation;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import yonasazela.lahordeapi.kenshi.entities.ItemEntity;
import yonasazela.lahordeapi.exceptions.DBException;
import yonasazela.lahordeapi.exceptions.ObjectNotFoundException;
import yonasazela.lahordeapi.kenshi.repositories.IItemRepository;

import java.util.List;

/**
 * Implementation of the Item repository. This class wraps an
 * {@link IItemRepository} instance and provides custom exception handling for
 * database operations.
 */
@Repository
@Primary
@RequiredArgsConstructor
public class ItemRepository {

	private final IItemRepository repository;

	private static final String DB_ERROR_SAVE = "saving object";
	private static final String DB_ERROR_RETRIEVE = "retrieving object";
	private static final String DB_ERROR_CHECK_EXISTENCE = "checking existence";
	private static final String DB_ERROR_DELETE = "deleting object";
	private static final String DB_ERROR_RETRIEVE_ALL = "retrieving all objects";

	// ===== SECTION 1 - CRUD DATABASE ONE ENTITY =====

	/**
	 * Saves a given entity.
	 *
	 * @param entity
	 *            the entity to save.
	 * @param <S>
	 *            the type of the entity.
	 * @return the saved entity.
	 * @throws DBException
	 *             if an error occurs while saving the entity.
	 */
	// CREATE / UPDATE
	public <S extends ItemEntity> S save(S entity) {
		try {
			return repository.save(entity);
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_SAVE, e);
		}
	}

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id
	 *            the id of the entity to retrieve.
	 * @return the entity with the given id.
	 * @throws ObjectNotFoundException
	 *             if no entity is found with the given id.
	 * @throws DBException
	 *             if an error occurs while retrieving the entity.
	 */
	// READ
	public ItemEntity findById(int id) {
		try {
			return repository.findById(id).orElseThrow(() -> ObjectNotFoundException.newObjectNotFoundException(id));
		} catch (ObjectNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_RETRIEVE, id, e);
		}
	}

	/**
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id
	 *            the id of the entity.
	 * @return true if an entity with the given id exists, false otherwise.
	 * @throws DBException
	 *             if an error occurs while checking for existence.
	 */
	// EXISTENCE CHECKS
	public boolean existsById(int id) {
		try {
			return repository.existsById(id);
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_CHECK_EXISTENCE, id, e);
		}
	}

	/**
	 * Returns whether an entity with the given name exists.
	 *
	 * @param name
	 *            the name of the entity.
	 * @return true if an entity with the given name exists, false otherwise.
	 * @throws DBException
	 *             if an error occurs while checking for existence.
	 */
	public boolean existsByName(String name) {
		try {
			return repository.existsByName(name);
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_CHECK_EXISTENCE, name, e);
		}
	}

	/**
	 * Deletes the entity with the given id.
	 *
	 * @param id
	 *            the id of the entity to delete.
	 * @throws DBException
	 *             if an error occurs while deleting the entity.
	 */
	// DELETE
	public void deleteById(int id) {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_DELETE, id, e);
		}
	}

	// ===== SECTION 2 - CRUD DATABASE MULTIPLE ENTITIES =====

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities.
	 * @throws DBException
	 *             if an error occurs while retrieving the entities.
	 */
	public @NonNull List<ItemEntity> findAll() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw DBException.newDBException(DB_ERROR_RETRIEVE_ALL, e);
		}
	}
}
