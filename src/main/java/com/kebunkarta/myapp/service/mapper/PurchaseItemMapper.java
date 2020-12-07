package com.kebunkarta.myapp.service.mapper;


import com.kebunkarta.myapp.domain.*;
import com.kebunkarta.myapp.service.dto.PurchaseItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseItem} and its DTO {@link PurchaseItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {BarangMapper.class, UnitMapper.class, TaxMapper.class, PurchaseMapper.class})
public interface PurchaseItemMapper extends EntityMapper<PurchaseItemDTO, PurchaseItem> {

    @Mapping(source = "catalog.id", target = "catalogId")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "tax.id", target = "taxId")
    @Mapping(source = "purchase.id", target = "purchaseId")
    PurchaseItemDTO toDto(PurchaseItem purchaseItem);

    @Mapping(source = "catalogId", target = "catalog")
    @Mapping(source = "unitId", target = "unit")
    @Mapping(source = "taxId", target = "tax")
    @Mapping(source = "purchaseId", target = "purchase")
    PurchaseItem toEntity(PurchaseItemDTO purchaseItemDTO);

    default PurchaseItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setId(id);
        return purchaseItem;
    }
}
