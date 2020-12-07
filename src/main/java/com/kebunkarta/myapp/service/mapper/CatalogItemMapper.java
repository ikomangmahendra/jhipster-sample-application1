package com.kebunkarta.myapp.service.mapper;


import com.kebunkarta.myapp.domain.*;
import com.kebunkarta.myapp.service.dto.CatalogItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CatalogItem} and its DTO {@link CatalogItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatalogItemMapper extends EntityMapper<CatalogItemDTO, CatalogItem> {



    default CatalogItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setId(id);
        return catalogItem;
    }
}
