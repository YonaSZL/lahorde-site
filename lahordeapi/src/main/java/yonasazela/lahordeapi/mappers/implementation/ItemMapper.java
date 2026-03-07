package yonasazela.lahordeapi.mappers.implementation;

import org.springframework.stereotype.Component;
import yonasazela.lahordeapi.dto.ItemDTO;
import yonasazela.lahordeapi.entities.ItemEntity;
import yonasazela.lahordeapi.mappers.IItemMapper;

@Component
public class ItemMapper implements IItemMapper {

    @Override
    public ItemDTO toDTO(ItemEntity entity) {
        if (entity == null)
            return null;

        return ItemDTO.builder().id(entity.getId()).name(entity.getName()).price(entity.getPrice())
                .weight(entity.getWeight()).size(entity.getSize()).priceForOneSlot(entity.getPriceForOneSlot()) // lecture
                // seule
                .description(entity.getDescription()).build();
    }

    @Override
    public ItemEntity toEntity(ItemDTO dto) {
        if (dto == null)
            return null;

        return ItemEntity.builder().id(dto.getId()).name(dto.getName()).price(dto.getPrice()).weight(dto.getWeight())
                .size(dto.getSize())
                // NE PAS SETTER priceForOneSlot
                .description(dto.getDescription()).build();
    }

    @Override
    public void updateEntityFromDTO(ItemDTO dto, ItemEntity entity) {
        if (dto == null || entity == null)
            return;

        // Mettre à jour uniquement les champs modifiables
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setWeight(dto.getWeight());
        entity.setSize(dto.getSize());
        // NE PAS TOUCHER PriceForOneSlot
        entity.setDescription(dto.getDescription());
    }
}
