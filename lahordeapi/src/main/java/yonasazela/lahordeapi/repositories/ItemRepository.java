package yonasazela.lahordeapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yonasazela.lahordeapi.entities.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    boolean existsByName(String name);
}
