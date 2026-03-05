package yonasazela.lahordeapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yonasazela.lahordeapi.entities.ItemEntity;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
	Optional<ItemEntity> findByName(String name);
}
