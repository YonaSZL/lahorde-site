package yonasazela.lahordeapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;

@Mapper(componentModel = "spring")
public interface IItemMapper {
    ItemDTO toDTO(ItemEntity entity);

    ItemEntity toEntity(ItemDTO dto);

    // Nouvelle méthode pour mettre à jour l'entity existante à partir du DTO
    void updateEntityFromDTO(ItemDTO dto, @MappingTarget ItemEntity entity);
}
