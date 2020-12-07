package com.kebunkarta.myapp.service.mapper;


import com.kebunkarta.myapp.domain.*;
import com.kebunkarta.myapp.service.dto.SalesItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalesItem} and its DTO {@link SalesItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {CatalogItemMapper.class, UnitMapper.class, TaxMapper.class, SalesMapper.class})
public interface SalesItemMapper extends EntityMapper<SalesItemDTO, SalesItem> {

    @Mapping(source = "catalog.id", target = "catalogId")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "tax.id", target = "taxId")
    @Mapping(source = "sales.id", target = "salesId")
    SalesItemDTO toDto(SalesItem salesItem);

    @Mapping(source = "catalogId", target = "catalog")
    @Mapping(source = "unitId", target = "unit")
    @Mapping(source = "taxId", target = "tax")
    @Mapping(source = "salesId", target = "sales")
    SalesItem toEntity(SalesItemDTO salesItemDTO);

    default SalesItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesItem salesItem = new SalesItem();
        salesItem.setId(id);
        return salesItem;
    }
}
