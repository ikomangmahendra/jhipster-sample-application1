package com.kebunkarta.myapp.service.mapper;


import com.kebunkarta.myapp.domain.*;
import com.kebunkarta.myapp.service.dto.PurchaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Purchase} and its DTO {@link PurchaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, GudangMapper.class})
public interface PurchaseMapper extends EntityMapper<PurchaseDTO, Purchase> {

    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    PurchaseDTO toDto(Purchase purchase);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "removeItems", ignore = true)
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "warehouseId", target = "warehouse")
    Purchase toEntity(PurchaseDTO purchaseDTO);

    default Purchase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Purchase purchase = new Purchase();
        purchase.setId(id);
        return purchase;
    }
}
